package com.relatosdepapel.catalogue_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//CLASE QUE IMITA LA TABLA "BOOK" EN LA BD CATALOGUE-SERVICE
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "El autor es obligatorio")
    @Column(nullable = false, length = 150)
    private String author;

    @NotNull(message = "La fecha de publicación es obligatoria")
    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @NotBlank(message = "La categoría es obligatoria")
    @Column(nullable = false, length = 100)
    private String category;

    @NotBlank(message = "El ISBN es obligatorio")
    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @NotNull(message = "La valoración es obligatoria")
    @Min(value = 1, message = "La valoración mínima es 1")
    @Max(value = 5, message = "La valoración máxima es 5")
    @Column(nullable = false)
    private Integer rating;

    @NotNull(message = "La visibilidad es obligatoria")
    @Column(nullable = false)
    private Boolean visible;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}