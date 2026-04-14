package com.asistente.alarmas.kafka;

import com.asistente.alarmas.eventos.EventoAlarmaCreada;
import com.asistente.alarmas.eventos.EventoNotaCreada;
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
public class ConsumidorNotas {

    private final AlarmaRepositorio repo;
    private final PublicadorEventosAlarma publicador;

    @KafkaListener(
            topics = "notas.eventos",
            groupId = "alarmas",
            properties = {
                    "spring.json.trusted.packages=com.asistente.*",
                    "spring.json.use.type.headers=false",
                    "spring.json.value.default.type=com.asistente.alarmas.eventos.EventoNotaCreada"
            }
    )
    public void onNotaCreada(@Payload EventoNotaCreada evt) {
        if (evt == null) {
            log.warn("EventoNotaCreada nulo");
            return;
        }

        log.info("Alarmas <- EventoNotaCreada idNota={} titulo={} fecha={}",
                evt.getId(), evt.getTitulo(), evt.getFecha());

        LocalDateTime cuando = null;
        if (evt.getFecha() != null && !evt.getFecha().isBlank()) {
            try {
                cuando = LocalDateTime.parse(evt.getFecha()); // ISO-8601
            } catch (Exception ex) {
                log.warn("Fecha inválida en EventoNotaCreada: '{}'", evt.getFecha());
            }
        }

        String contenido = evt.getContenido() == null ? "" : evt.getContenido();

        Alarma alarma = repo.save(new Alarma(null, evt.getTitulo(), contenido, cuando));
        log.info("Alarma creada desde nota idAlarma={} programadaPara={}", alarma.getId(), alarma.getFecha());

        publicador.publicarCreada(
                EventoAlarmaCreada.builder()
                        .id(alarma.getId())
                        .titulo(alarma.getTitulo())
                        .contenido(alarma.getContenido() == null ? "" : alarma.getContenido())
                        .fecha(alarma.getFecha() != null ? alarma.getFecha().toString() : null)
                        .build()
        );
    }
}

