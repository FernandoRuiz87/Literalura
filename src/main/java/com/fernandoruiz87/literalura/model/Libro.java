package com.fernandoruiz87.literalura.model;

import com.fernandoruiz87.literalura.dto.LibroDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private Double cantidadDescargas;
    @ElementCollection(fetch = FetchType.EAGER)  // Element collection sirve para colecciones de tipos simples
    private List<String> idiomas;
    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

    public Libro() {
    }

    public Libro(LibroDTO libro) {
        this.titulo = libro.titulo();
        this.cantidadDescargas = Double.valueOf(libro.cantidadDescargas());
        this.idiomas = libro.idiomas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Double cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return String.format("""
            ---------LIBRO-----------
                TITULO: %s
                CANTIDAD DESCARGAS: %s
                IDIOMAS: %s
                AUTORES: {%s}
            -------------------------
            """,
                titulo,
                cantidadDescargas != null ? cantidadDescargas : "null",
                idiomas != null ? idiomas : "null",
                autores != null ? autores.stream()
                        .map(Autor::getNombre)
                        .toList().toString() : "null"
        );
    }
}
