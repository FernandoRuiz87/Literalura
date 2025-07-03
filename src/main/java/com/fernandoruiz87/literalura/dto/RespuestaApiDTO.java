package com.fernandoruiz87.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record RespuestaApiDTO(
        @JsonAlias("results") List<LibroDTO> resultados
) {
}
