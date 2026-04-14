package com.asistente.alarmas.eventos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventoNotaCreadaTest {

    @Test
    void builderYSettersFuncionan() {
        EventoNotaCreada evento = EventoNotaCreada.builder()
                .id("n-1")
                .titulo("Llamar")
                .contenido("Llamar al médico")
                .fecha("2026-03-18T09:30:00")
                .build();

        assertEquals("n-1", evento.getId());
        assertEquals("Llamar", evento.getTitulo());
        assertEquals("Llamar al médico", evento.getContenido());
        assertEquals("2026-03-18T09:30:00", evento.getFecha());

        evento.setTitulo("Nuevo título");
        assertEquals("Nuevo título", evento.getTitulo());
    }
}
