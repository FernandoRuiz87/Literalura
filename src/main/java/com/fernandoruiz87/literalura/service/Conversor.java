package com.fernandoruiz87.literalura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class Conversor {
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Deserializa el último elemento de una lista de un JSON.
     *
     * @param json          Datos en formato JSON
     * @param nombreCampo Nombre del campo lista (ej. "results")
     * @param clase     Clase del tipo destino (ej. LibroDTO.class)
     * @param <T>           Tipo genérico
     * @return Optional<T> con el último elemento, o vacío si no hay elementos.
     */
    public static <T> Optional<T> obtenerDatos(String json, String nombreCampo, Class<T> clase) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(json);
            JsonNode listNode = root.get(nombreCampo);
            if (listNode != null && listNode.isArray() && !listNode.isEmpty()) {
                JsonNode firstNode = listNode.get(0);
                T result = mapper.treeToValue(firstNode, clase);
                return Optional.ofNullable(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
