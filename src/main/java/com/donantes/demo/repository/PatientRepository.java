package com.donantes.demo.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.donantes.demo.entity.Patient;

@Repository
public interface PatientRepository  extends Neo4jRepository<Patient, String> {
    //buscar pacientes por tipo de sangre y urgencia
    List<Patient> findByTipoSangreAndUrgencia(String tipoSangre, int urgencia);

}
