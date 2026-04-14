package com.asistente.alarmas.servicio;

import com.asistente.alarmas.eventos.EventoAlarmaCreada;
import com.asistente.alarmas.eventos.PublicadorEventosAlarma;
import com.asistente.alarmas.modelo.Alarma;
import com.asistente.alarmas.repositorio.AlarmaRepositorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmaServicio {

    private final AlarmaRepositorio repo;
    private final PublicadorEventosAlarma publicador;

    public Alarma crear(Alarma nueva, String usuarioActual) {
        Alarma guardada = repo.save(nueva);

        EventoAlarmaCreada evt = EventoAlarmaCreada.builder()
                .id(guardada.getId())
                .titulo(guardada.getTitulo())
                .contenido(guardada.getContenido())
                .fecha(guardada.getFecha() != null ? guardada.getFecha().toString() : null)
                .build();

        log.info("Publicando EventoAlarmaCreada id={} titulo={}", evt.getId(), evt.getTitulo());
        publicador.publicarCreada(evt);

        return guardada;
    }
}
