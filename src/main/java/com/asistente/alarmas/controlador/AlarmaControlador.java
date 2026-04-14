package com.asistente.alarmas.controlador;

import com.asistente.alarmas.modelo.Alarma;
import com.asistente.alarmas.repositorio.AlarmaRepositorio;
import com.asistente.alarmas.servicio.AlarmaServicio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/alarmas")
@RequiredArgsConstructor
public class AlarmaControlador {

    private final AlarmaRepositorio alarmarepositorio;
    private final AlarmaServicio servicio;

    @GetMapping
    public List<Alarma> getAll() {
        log.debug("Listar alarmas");
        return alarmarepositorio.findAll();
    }

    @PostMapping
    public Alarma save(@RequestBody Alarma alarma) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuarioActual = (auth != null ? auth.getName() : null);

        log.info("Crear alarma titulo='{}'", alarma != null ? alarma.getTitulo() : null);

        // Crear a través del servicio (emite evento a Kafka)
        Alarma guardada = servicio.crear(alarma, usuarioActual);

        log.info("Alarma creada id='{}'", guardada != null ? guardada.getId() : null);
        return guardada;
    }

    @PutMapping("/{id}")
    public Alarma update(@PathVariable String id, @RequestBody Alarma nueva) {
        log.info("Actualizar alarma id='{}' titulo='{}'", id, nueva != null ? nueva.getTitulo() : null);
        return alarmarepositorio.findById(id).map(a -> {
            assert nueva != null;
            a.setTitulo(nueva.getTitulo());
            a.setContenido(nueva.getContenido());
            if (nueva.getFecha() != null) {
                a.setFecha(nueva.getFecha());
            }
            Alarma actualizada = alarmarepositorio.save(a);
            log.info("Alarma actualizada id='{}'", actualizada.getId());
            return actualizada;
        }).orElseGet(() -> {
            assert nueva != null;
            nueva.setId(id);
            Alarma creada = alarmarepositorio.save(nueva);
            log.info("Alarma creada (upsert) id='{}'", creada.getId());
            return creada;
        });
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("Borrar alarma id='{}'", id);
        alarmarepositorio.deleteById(id);
        log.info("Alarma borrada id='{}'", id);
    }
}

