package com.asistente.alarmas.logs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ManejadorGlobalDeExcepciones {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validacion(MethodArgumentNotValidException ex) {
        log.warn("Validación fallida: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(Map.of("error", "error_de_validacion"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> peticionInvalida(IllegalArgumentException ex) {
        log.warn("Petición inválida: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(Map.of("error", "peticion_invalida"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> generica(Exception ex) {
        log.error("Error no controlado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "error_interno"));
    }
}
