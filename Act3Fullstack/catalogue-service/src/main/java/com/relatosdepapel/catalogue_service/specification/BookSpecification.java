package com.relatosdepapel.catalogue_service.specification;

import com.relatosdepapel.catalogue_service.model.Book;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

//DEFINE LOS FILTROS POR LOS QUE SE PUEDE BUSCAR UN LIBRO
public class BookSpecification {

    //FILTRA POR TITULO
    public static Specification<Book> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%"
            );
        };
    }
    //FILTRA POR AUTOR
    public static Specification<Book> hasAuthor(String author) {
        return (root, query, criteriaBuilder) -> {
            if (author == null || author.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("author")),
                    "%" + author.toLowerCase() + "%"
            );
        };
    }
    //FILTRA POR FECHA DE PUBLICACION
    public static Specification<Book> hasPublicationDate(LocalDate publicationDate) {
        return (root, query, criteriaBuilder) -> {
            if (publicationDate == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("publicationDate"), publicationDate);
        };
    }
    //FILTRA POR CATEGORIA
    public static Specification<Book> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null || category.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("category")),
                    "%" + category.toLowerCase() + "%"
            );
        };
    }
    //FILTRA POR ISBN
    public static Specification<Book> hasIsbn(String isbn) {
        return (root, query, criteriaBuilder) -> {
            if (isbn == null || isbn.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("isbn"), isbn);
        };
    }

    //FILTRA POR VALORACION
    public static Specification<Book> hasRating(Integer rating) {
        return (root, query, criteriaBuilder) -> {
            if (rating == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("rating"), rating);
        };
    }

    //FILTRA POR VISIBILIDAD
    public static Specification<Book> hasVisible(Boolean visible) {
        return (root, query, criteriaBuilder) -> {
            if (visible == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("visible"), visible);
        };
    }
}