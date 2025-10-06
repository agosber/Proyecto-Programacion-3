package com.donantes.demo.algorithms;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class BFSService {

    private final Neo4jClient neo4jClient;

//objetivo: explorar todos los donantes cercanos a un hospital o paciente dentro de cierto radio o nivel de relación
    public List<String> findNearbyDonors(String patientId, int depth){
        
        //Desde el paciente, explorá en anchura (como BFS) todas las relaciones COMPATIBLE_CON o UBICADO_EN hasta $depth niveles.
        //usamos APOC para hacer el recorrido (libreria de neo4j)
        String query = "MATCH (p:Paciente {id: $patientId})\n" + 
                        "            CALL apoc.path.expand(p, 'COMPATIBLE_CON|UBICADO_EN', '', 1, $depth)\n" + 
                        "            YIELD path\n" + 
                        "            WITH DISTINCT last(nodes(path)) AS node\n" + 
                        "            WHERE node:Donante\n" + 
                        "            RETURN node.id AS donorId";
        return neo4jClient.query(query)
        .bindAll(java.util.Map.of("patientId", patientId, "depth", depth))
        .fetch()
        .all()
        .stream()
        .map(m-> (String) m.get("donorId"))
        .collect(Collectors.toList());
    }

}
