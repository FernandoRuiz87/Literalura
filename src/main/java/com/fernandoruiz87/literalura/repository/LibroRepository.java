package com.fernandoruiz87.literalura.repository;

import com.fernandoruiz87.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    @Query("SELECT l FROM Libro l JOIN l.idiomas i WHERE LOWER(i) = LOWER(:idioma)")
    List<Libro> buscarLibrosPorIdiomaIgnoreCase(@Param("idioma") String idioma);

}
