package com.asistente.alarmas.logs;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@Order(2)
public class FiltroAccesoHttp implements Filter {

    @Override
    public void doFilter(ServletRequest solicitud, ServletResponse respuesta, FilterChain cadena)
            throws IOException, ServletException {

        long inicio = System.currentTimeMillis();
        HttpServletRequest req = (HttpServletRequest) solicitud;
        HttpServletResponse resp = (HttpServletResponse) respuesta;

        try {
            cadena.doFilter(solicitud, respuesta);
        } finally {
            long ms = System.currentTimeMillis() - inicio;
            int estado = resp.getStatus();

            log.info("HTTP {} {} -> {} ({} ms)",
                    req.getMethod(), req.getRequestURI(), estado, ms);
        }
    }
}
