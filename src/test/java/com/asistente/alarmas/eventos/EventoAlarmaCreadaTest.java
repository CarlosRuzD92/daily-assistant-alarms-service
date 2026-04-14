package com.asistente.alarmas.eventos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventoAlarmaCreadaTest {

    @Test
    void builderConstruyeEventoCorrectamente() {
        EventoAlarmaCreada evento = EventoAlarmaCreada.builder()
                .id("a-1")
                .titulo("Beber agua")
                .contenido("Recordatorio")
                .fecha("2026-03-17T12:00:00")
                .build();

        assertEquals("a-1", evento.getId());
        assertEquals("Beber agua", evento.getTitulo());
        assertEquals("Recordatorio", evento.getContenido());
        assertEquals("2026-03-17T12:00:00", evento.getFecha());
    }
}
