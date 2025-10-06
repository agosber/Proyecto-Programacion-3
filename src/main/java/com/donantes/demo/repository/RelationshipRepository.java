package com.donantes.demo.repository;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.donantes.demo.entity.Donor;

@Repository
public interface RelationshipRepository extends CrudRepository<Donor, String>{
    // Crear relación Donante → Paciente (COMPATIBLE_CON)
    @Query("MATCH (d:Donante {id: $donanteId}), (p:Paciente {id: $pacienteId}) " +
           "MERGE (d)-[:COMPATIBLE_CON]->(p)")
    void createCompatibility(String donanteId, String pacienteId);

    // Crear relación Donante/Paciente → Hospital (UBICADO_EN)
    @Query("MATCH (h:Hospital {id: $hospitalId}) " +
           "OPTIONAL MATCH (d:Donante {id: $personaId}) " +
           "OPTIONAL MATCH (p:Paciente {id: $personaId}) " +
           "FOREACH (_ IN CASE WHEN d IS NOT NULL THEN [1] ELSE [] END | " +
           "MERGE (d)-[:UBICADO_EN]->(h)) " +
           "FOREACH (_ IN CASE WHEN p IS NOT NULL THEN [1] ELSE [] END | " +
           "MERGE (p)-[:UBICADO_EN]->(h))")
    void assignHospital(String personaId, String hospitalId);

}
