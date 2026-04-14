package com.asistente.alarmas.modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AlarmaTest {

    @Test
    void constructorYGettersFuncionan() {
        LocalDateTime fecha = LocalDateTime.of(2026, 3, 17, 10, 30);

        Alarma alarma = new Alarma("id-1", "Titulo", "Contenido", fecha);

        assertEquals("id-1", alarma.getId());
        assertEquals("Titulo", alarma.getTitulo());
        assertEquals("Contenido", alarma.getContenido());
        assertEquals(fecha, alarma.getFecha());
    }
}
