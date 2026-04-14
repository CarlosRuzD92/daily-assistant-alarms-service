package com.asistente.alarmas.repositorio;


import com.asistente.alarmas.modelo.Alarma;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AlarmaRepositorio extends MongoRepository<Alarma, String> {}

