package com.donantes.demo.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.donantes.demo.entity.Hospital;

@Repository
public interface HospitalRepository extends Neo4jRepository<Hospital, String> {
    //buscar hospital por cerca de una ubicacion (latitud y longitud) y un radio en km
}
