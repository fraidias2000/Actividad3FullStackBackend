package com.relatosdepapel.orders_service.service;

import com.relatosdepapel.orders_service.client.CatalogueClient;
import com.relatosdepapel.orders_service.dto.response.CatalogueBookResponse;
import com.relatosdepapel.orders_service.dto.request.CreateOrderRequest;
import com.relatosdepapel.orders_service.exception.CatalogueServiceUnavailableException;
import com.relatosdepapel.orders_service.exception.InvalidOrderException;
import com.relatosdepapel.orders_service.model.Order;
import com.relatosdepapel.orders_service.model.OrderItem;
import com.relatosdepapel.orders_service.repository.OrderRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.relatosdepapel.orders_service.client.UsersClient;
import com.relatosdepapel.orders_service.dto.response.UserProfileResponse;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CatalogueClient catalogueClient;
    private final EmailEventPublisher emailEventPublisher;
    private final UsersClient usersClient;

    public OrderService(
            OrderRepository orderRepository,
            CatalogueClient catalogueClient,
            EmailEventPublisher emailEventPublisher,
            UsersClient usersClient
    ) {
        this.orderRepository = orderRepository;
        this.catalogueClient = catalogueClient;
        this.emailEventPublisher = emailEventPublisher;
        this.usersClient = usersClient;
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con id: " + id));
    }

    public List<Order> findRecentOrdersByUserId(String userId) {
        return orderRepository.findTop10ByUserIdOrderByCreatedAtDesc(userId);
    }

    @Transactional
    public Order create(CreateOrderRequest request, String accessToken) {
        UserProfileResponse user = getAuthenticatedUser(accessToken);
        //AGRUPA LOS LIBROS POR ID
        Map<Long, Integer> requestedItems = groupQuantitiesByBookId(request);

        //CREA LA CABECERA
        Order order = Order.builder()
                .userId(request.userId())
                .status("CONFIRMED")
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> requestedItem : requestedItems.entrySet()) {
            Long bookId = requestedItem.getKey();
            Integer quantity = requestedItem.getValue();

            //COMPRUEBA SI HAY STOCK Y  ESTÁ VISIBLE
            CatalogueBookResponse book = findAndValidateBook(bookId, quantity);

            BigDecimal subtotal = book.price().multiply(BigDecimal.valueOf(quantity));

            //ACTUALIZA STOCK
            catalogueClient.decreaseStock(bookId, quantity);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .bookId(book.id())
                    .isbn(book.isbn())
                    .title(book.title())
                    .quantity(quantity)
                    .unitPrice(book.price())
                    .subtotal(subtotal)
                    .build();

            order.getItems().add(orderItem);
            totalAmount = totalAmount.add(subtotal);
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        // ENVIA EMAIL A TRAVES DE RABBITMQ DESPUÉS DE GUARDAR, PARA ELLO LLAMA A USER-SERVICE PARA OBTENER EL EMAIL
        emailEventPublisher.publishOrderCreatedEmail(
                user.email(),
                savedOrder.getId()
        );

        return savedOrder;
    }

    //OBTIENE EL USUARIO EN BASE A UN JWT
    private UserProfileResponse getAuthenticatedUser(String accessToken) {
        try {
            UserProfileResponse user = usersClient.getMyProfile(accessToken);

            if (user == null || user.id() == null || user.email() == null || user.email().isBlank()) {
                throw new InvalidOrderException("No se ha podido obtener el usuario autenticado");
            }

            return user;
        } catch (FeignException.Unauthorized | FeignException.Forbidden ex) {
            throw new InvalidOrderException("Token inválido o usuario no autorizado");
        } catch (FeignException ex) {
            throw new InvalidOrderException("No se ha podido obtener el usuario desde users-service");
        }
    }

    //AGRUPA LOS LIBROS POR ID
    private Map<Long, Integer> groupQuantitiesByBookId(CreateOrderRequest request) {
        Map<Long, Integer> requestedItems = new LinkedHashMap<>();

        request.items().forEach(item ->
                requestedItems.merge(item.bookId(), item.quantity(), Integer::sum)
        );

        return requestedItems;
    }

    //LLAMA AL MICROSERVICIO CATALOGUE Y COMPRUEBA QUE TENGA STOCK Y ESTÉ VISIBLE
    private CatalogueBookResponse findAndValidateBook(Long bookId, Integer quantity) {
        CatalogueBookResponse book;

        try {
            book = catalogueClient.findBookById(bookId);
        } catch (FeignException.NotFound ex) {
            throw new InvalidOrderException("No existe el libro con id: " + bookId);
        } catch (FeignException ex) {
            throw new CatalogueServiceUnavailableException(
                    "No se ha podido validar el libro con id " + bookId + " en catalogue-service"
            );
        }

        if (!Boolean.TRUE.equals(book.visible())) {
            throw new InvalidOrderException(
                    "El libro con id " + bookId + " no está visible y no se puede comprar"
            );
        }

        if (book.stock() == null || book.stock() < quantity) {
            throw new InvalidOrderException(
                    "Stock insuficiente para el libro con id " + bookId
                            + ". Stock disponible: " + book.stock()
                            + ", cantidad solicitada: " + quantity
            );
        }

        return book;
    }
}
