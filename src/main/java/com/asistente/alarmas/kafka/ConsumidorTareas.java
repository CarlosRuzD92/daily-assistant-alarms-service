package com.asistente.alarmas.kafka;

import com.asistente.alarmas.eventos.EventoAlarmaCreada;
import com.asistente.alarmas.eventos.EventoTareaCreada;
import com.asistente.alarmas.eventos.PublicadorEventosAlarma;
import com.asistente.alarmas.modelo.Alarma;
import com.asistente.alarmas.repositorio.AlarmaRepositorio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumidorTareas {

    private final AlarmaRepositorio repo;
    private final PublicadorEventosAlarma publicador;

    @KafkaListener(
            topics = "tareas.eventos",
            groupId = "alarmas",
            properties = {
                    "spring.json.trusted.packages=com.asistente.*",
                    "spring.json.use.type.headers=false",
                    "spring.json.value.default.type=com.asistente.alarmas.eventos.EventoTareaCreada"
            }
    )
    public void onTareaCreada(@Payload EventoTareaCreada evt) {
        if (evt == null) {
            log.warn("EventoTareaCreada nulo");
            return;
        }

        log.info("Alarmas <- EventoTareaCreada id={} titulo={} fecha={} prioridad={} completada={}",
                evt.getId(), evt.getTitulo(), evt.getFecha(), evt.getPrioridad(), evt.getCompletada());

        LocalDateTime cuando = null;
        if (evt.getFecha() != null && !evt.getFecha().isBlank()) {
            try {
                cuando = LocalDateTime.parse(evt.getFecha());
            } catch (Exception ex) {
                log.warn("Fecha inválida en EventoTareaCreada: '{}'", evt.getFecha());
            }
        }

        String prioridad = evt.getPrioridad() == null ? "SIN_PRIORIDAD" : evt.getPrioridad();
        String completada = evt.getCompletada() == null ? "desconocido" : (evt.getCompletada() ? "sí" : "no");
        String contenido = "Tarea creada (prioridad: " + prioridad + ", completada: " + completada + ")";

        Alarma alarma = repo.save(new Alarma(null, evt.getTitulo(), contenido, cuando));
        log.info("Alarma creada desde tarea idAlarma={} programadaPara={}", alarma.getId(), alarma.getFecha());

        EventoAlarmaCreada evento = EventoAlarmaCreada.builder()
                .id(alarma.getId())
                .titulo(alarma.getTitulo())
                .contenido(alarma.getContenido() == null ? "" : alarma.getContenido())
                .fecha(alarma.getFecha() != null ? alarma.getFecha().toString() : null)
                .build();
        publicador.publicarCreada(evento);
    }
}
