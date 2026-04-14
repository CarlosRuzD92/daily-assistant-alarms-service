package com.asistente.alarmas.eventos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
/**
 * Evento que se emite cuando se crea una Alarma.
 * - Inmutable (@Value)
 * - Construcción fluida (@Builder)
 * - No envía campos null en el JSON (@JsonInclude NON_NULL)
 */
@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventoAlarmaCreada {
    String id;
    String titulo;
    String contenido;
    String fecha;
}
