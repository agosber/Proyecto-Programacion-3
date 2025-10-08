package com.donantes.demo.entity;

import java.util.List;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Node("Paciente")
public class Patient {
    @Id
    private String id;

    private String nombre;
    private String tipoSangre;
    private List<String> organosNecesarios;
    private int urgencia;
    private int prioridad;
    private double lat;
    private double lon;

    // Relación inversa: Paciente compatible con Donante
    @JsonIgnore
    @Relationship(type = "COMPATIBLE_CON", direction = Relationship.Direction.INCOMING)
    private List<Donor> donantes;
    
    // Relación: Paciente ubicado en Hospital
    @Relationship(type = "UBICADO_EN")
    private Hospital hospital;
}
