package com.donantes.demo.algorithms;

import java.util.Map;

import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class DijkstraService {

    private final Neo4jClient neo4jClient;

    //objetivo:encontrar la ruta mas rapida para transportar organos entre un donante y un paciente
    public Map<String, Object> shortestPath(String donorId, String hospitalId){
        String query = 
            "MATCH (d:Donante {id: $donorId}), (h:Hospital {id: $hospitalId})\n" +
            "CALL apoc.algo.dijkstra(d, h, 'UBICADO_EN|CONECTA_A', 'distancia')\n" +
            "YIELD path, weight\n" +
            "RETURN [n IN nodes(path) | n.id] AS nodos, weight AS distanciaTotal";
            return neo4jClient.query(query)
            .bindAll(Map.of("donorId", donorId, "hospitalId", hospitalId))
            .fetch()
            .first()
            .orElse(Map.of("message","No se encontró una ruta entre el donante y el hospital"));
            
    }

}
