package com.asistente.alarmas.modelo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
/**
 * @Document para que Spring sepa que esta clase va a ser guardada en MongoDB.
 * La propiedad "collection" define el nombre exacto en MongoDB.
 */
@Document(collection = "alarmas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Alarma {
    @Id
    private String id;
    private String titulo;
    private String contenido;
    private LocalDateTime fecha;
}
