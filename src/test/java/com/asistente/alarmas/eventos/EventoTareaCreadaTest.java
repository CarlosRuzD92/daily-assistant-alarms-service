package com.asistente.alarmas.eventos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EventoTareaCreadaTest {

    @Test
    void builderYGettersFuncionan() {
        EventoTareaCreada evento = EventoTareaCreada.builder()
                .id("t-1")
                .titulo("Entregar práctica")
                .fecha("2026-03-19T18:00:00")
                .completada(false)
                .prioridad("ALTA")
                .build();

        assertEquals("t-1", evento.getId());
        assertEquals("Entregar práctica", evento.getTitulo());
        assertEquals("2026-03-19T18:00:00", evento.getFecha());
        assertFalse(evento.getCompletada());
        assertEquals("ALTA", evento.getPrioridad());
    }
}
