package com.relatosdepapel.catalogue_service.repository;

import com.relatosdepapel.catalogue_service.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/*
*INTERFAZ PARA DECIR A SPRING COMO ACCEDER A LA BD
* JpaSpecificationExecutor<Book> SIRVE PARA HACER BUSQUEDAS COMBINADAS POR title, author,publicationDate,category,isbn,rating,visible
*/
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);
}