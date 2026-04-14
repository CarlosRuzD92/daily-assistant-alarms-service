package com.asistente.alarmas.eventos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicadorEventosAlarma {

    private static final String TOPIC = "alarmas.programadas";

    private final KafkaTemplate<String, Object> kafka;

    public void publicarCreada(EventoAlarmaCreada evt) {
        kafka.send(TOPIC, evt.getId(), evt)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Fallo publicando EventoAlarmaCreada id={}, error={}", evt.getId(), ex.toString(), ex);
                    } else {
                        var meta = result.getRecordMetadata();
                        log.info("EventoAlarmaCreada OK id={} topic={} partition={} offset={}",
                                evt.getId(), meta.topic(), meta.partition(), meta.offset());
                    }
                });
    }
}
