package com.donantes.demo.entity;

import java.util.List;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Data;

@Data
@Node("Hospital")
public class Hospital {
    @Id
    private String id;

    private String nombre;
    private String ciudad;
    private double lat;
    private double lon;
    private int capacidad;

    // Donantes y pacientes en el hospital
    @Relationship(type = "UBICADO_EN", direction = Relationship.Direction.INCOMING)
    private List<Donor> donantes;

    @Relationship(type = "UBICADO_EN", direction = Relationship.Direction.INCOMING)
    private List<Patient> pacientes;

    // Conexiones a otros hospitales (para rutas Dijkstra)
    @Relationship(type = "CONEXION")
    private List<Hospital> conexiones;
    
}
