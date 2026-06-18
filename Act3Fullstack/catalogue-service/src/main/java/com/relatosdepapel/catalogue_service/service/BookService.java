package com.relatosdepapel.catalogue_service.service;

import com.relatosdepapel.catalogue_service.model.Book;
import com.relatosdepapel.catalogue_service.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.relatosdepapel.catalogue_service.specification.BookSpecification;
import org.springframework.data.jpa.domain.Specification;
import com.relatosdepapel.catalogue_service.exception.BookNotFoundException;
import com.relatosdepapel.catalogue_service.exception.DuplicateISBNBookException;
import java.time.LocalDate;
import java.util.List;

//CLASE JAVA DONDE SE IMPLEMENTA LA INTERFAZ JPA Y LA LÓGICA DE NEGOCIO
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado con id: " + id));
    }

    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado con ISBN: " + isbn));
    }

    public Book create(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new DuplicateISBNBookException("Ya existe un libro con ISBN: " + book.getIsbn());
        }

        return bookRepository.save(book);
    }

    public Book update(Long id, Book updatedBook) {
        Book existingBook = findById(id);

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPublicationDate(updatedBook.getPublicationDate());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setRating(updatedBook.getRating());
        existingBook.setVisible(updatedBook.getVisible());
        existingBook.setStock(updatedBook.getStock());
        existingBook.setPrice(updatedBook.getPrice());
        existingBook.setUrlImage(updatedBook.getUrlImage());

        return bookRepository.save(existingBook);
    }

    public Book partialUpdate(Long id, Book updatedBook) {
        Book existingBook = findById(id);

        if (updatedBook.getTitle() != null) {
            existingBook.setTitle(updatedBook.getTitle());
        }

        if (updatedBook.getAuthor() != null) {
            existingBook.setAuthor(updatedBook.getAuthor());
        }

        if (updatedBook.getPublicationDate() != null) {
            existingBook.setPublicationDate(updatedBook.getPublicationDate());
        }

        if (updatedBook.getCategory() != null) {
            existingBook.setCategory(updatedBook.getCategory());
        }

        if (updatedBook.getIsbn() != null) {
            existingBook.setIsbn(updatedBook.getIsbn());
        }

        if (updatedBook.getRating() != null) {
            existingBook.setRating(updatedBook.getRating());
        }

        if (updatedBook.getVisible() != null) {
            existingBook.setVisible(updatedBook.getVisible());
        }

        if (updatedBook.getStock() != null) {
            existingBook.setStock(updatedBook.getStock());
        }

        if (updatedBook.getPrice() != null) {
            existingBook.setPrice(updatedBook.getPrice());
        }
        if (updatedBook.getUrlImage() != null) {
            existingBook.setUrlImage(updatedBook.getUrlImage());
        }

        return bookRepository.save(existingBook);
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Libro no encontrado con id: " + id);
        }

        bookRepository.deleteById(id);
    }

    public List<Book> search(
            String title,
            String author,
            LocalDate publicationDate,
            String category,
            String isbn,
            Integer rating,
            Boolean visible
    ) {
        Specification<Book> specification = Specification
                .where(BookSpecification.hasTitle(title))
                .and(BookSpecification.hasAuthor(author))
                .and(BookSpecification.hasPublicationDate(publicationDate))
                .and(BookSpecification.hasCategory(category))
                .and(BookSpecification.hasIsbn(isbn))
                .and(BookSpecification.hasRating(rating))
                .and(BookSpecification.hasVisible(visible));

        return bookRepository.findAll(specification);
    }

    @Transactional
    public Book decreaseStock(Long id, Integer quantity) {
        Book book = findById(id);

        if (!Boolean.TRUE.equals(book.getVisible())) {
            throw new RuntimeException("El libro no está visible");
        }

        if (book.getStock() == null || book.getStock() < quantity) {
            throw new RuntimeException("Stock insuficiente");
        }

        book.setStock(book.getStock() - quantity);

        return bookRepository.save(book);
    }

}