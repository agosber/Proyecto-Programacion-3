package com.donantes.demo.entity;

import java.util.List;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Node("Donante")
public class Donor {
    @Id
    private String id;

    private String nombre;
    private String tipoSangre;
    private List<String> organos;
    private boolean disponibilidad;
    private int prioridad;
    private double lat;
    private double lon;

    // Relación: Donante compatible con Paciente
    @JsonIgnore
    @Relationship(type= "COMPATIBLE_CON")
    private List<Patient> compatibles;

    // Relación: Donante ubicado en Hospital
    @Relationship(type = "UBICADO_EN")
    private Hospital hospital;


}
