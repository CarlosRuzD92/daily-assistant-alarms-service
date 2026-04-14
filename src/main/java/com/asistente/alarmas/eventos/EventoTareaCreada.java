package com.asistente.alarmas.eventos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventoTareaCreada {
    private String id;
    private String titulo;
    private String fecha;
    private Boolean completada;
    private String prioridad;
}
