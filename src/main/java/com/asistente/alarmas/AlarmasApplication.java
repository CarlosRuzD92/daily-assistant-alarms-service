package com.asistente.alarmas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class AlarmasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlarmasApplication.class, args);
	}

}
