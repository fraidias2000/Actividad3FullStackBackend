package com.relatosdepapel.catalogue_service.controller;

import com.relatosdepapel.catalogue_service.model.Book;
import com.relatosdepapel.catalogue_service.service.BookService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/books")

//CLASE JAVA DONDE SE EXPONEN LOS ENDPOINTS
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //ENDPOINT OBTENCIÓN DE TODOS LOS LIBROS
    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    //ENDPOINT BUSCAR POR ID
    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    //ENDPOINT CREACIÓN LIBRO
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@Valid @RequestBody Book book) {
        return bookService.create(book);
    }

    //ENDPOINT ACTUALIZACIÓN TOTAL LIBRO
    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @Valid @RequestBody Book book) {
        return bookService.update(id, book);
    }

    //ENDPOINT ACTUALIZACIÓN PARCIAL LIBRO
    @PatchMapping("/{id}")
    public Book partialUpdate(@PathVariable Long id, @RequestBody Book book) {
        return bookService.partialUpdate(id, book);
    }

    //ENDPOINT ELIMINACIÓN LIBRO
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    //ENDPOINT PARA FILTRAR LIBROS
    @GetMapping("/search")
    public List<Book> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publicationDate,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Boolean visible
    ) {
        return bookService.search(title, author, publicationDate, category, isbn, rating, visible);
    }
    //ENDPOINT PARA ACTUALIZAR STOCK

    @PostMapping("/updateStock/{id}")
    public Book decreaseStock(@PathVariable Long id,
                            @RequestParam Integer quantity) {
        return bookService.decreaseStock(id, quantity);
    }

}