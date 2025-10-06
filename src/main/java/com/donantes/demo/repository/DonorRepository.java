package com.donantes.demo.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.donantes.demo.entity.Donor;

@Repository
public interface DonorRepository extends Neo4jRepository<Donor, String> {
    //buscar donantes por tipo de sangre y disponibilidad
    List<Donor> findByTipoSangreAndDisponibilidad(String tipoSangre, boolean disponibilidad);
}
