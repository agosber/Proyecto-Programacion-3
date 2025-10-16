package com.donantes.demo.service;


import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DijkstraService {

//Encuentra la ruta más corta entre dos hospitales en un grafo ponderado usando el algoritmo de Dijkstra.
//Usa una cola de prioridad para elegir el nodo con menor distancia acumulada

//Poda: cuando se extrae el nodo destino del heap, se detiene el algoritmo,
//evitando explorar caminos más largos (optimización natural del propio Dijkstra).

    private final Neo4jClient neo4jClient;

    public DijkstraService(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }

    public Map<String, Object> shortestPath(String fromHospitalId, String toHospitalId) {
        // Obtener todas las conexiones entre hospitales
        String query = """
            MATCH (a:Hospital)-[r:CONEXION]->(b:Hospital)
            RETURN a.id AS from, b.id AS to, r.distancia AS weight
            """;

        Collection<Map<String, Object>> results = neo4jClient.query(query)
                .fetch()
                .all();

        // Construir el grafo en memoria
        Graph graph = new Graph();
        for (Map<String, Object> row : results) {
            String from = (String) row.get("from");
            String to = (String) row.get("to");
            double weight = ((Number) row.get("weight")).doubleValue();
            graph.addEdge(from, to, weight);
            graph.addEdge(to, from, weight); // Grafo no dirigido
        }

        // Ejecutar Dijkstra
        Graph.DijkstraResult result = graph.dijkstra(fromHospitalId, toHospitalId);

        // Devolver resultado
        if (result.getPath().isEmpty()) {
            return Map.of("message", "No se encontró camino entre los hospitales especificados.");
        }

        return Map.of(
                "from", fromHospitalId,
                "to", toHospitalId,
                "distance", result.getCost(),
                "path", result.getPath()
        );
    }

    /**
     * Clase interna para representar el grafo y ejecutar Dijkstra.
     */
    static class Graph {
        private final Map<String, List<Edge>> adj = new HashMap<>();

        public void addEdge(String u, String v, double w) {
            adj.computeIfAbsent(u, k -> new ArrayList<>()).add(new Edge(v, w));
        }

        public DijkstraResult dijkstra(String source, String target) {
            Map<String, Double> dist = new HashMap<>();
            Map<String, String> prev = new HashMap<>();

            for (String node : adj.keySet()) dist.put(node, Double.POSITIVE_INFINITY);
            dist.put(source, 0.0);

            PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.dist));
            pq.add(new Node(source, 0.0));

            while (!pq.isEmpty()) {
                Node current = pq.poll();
                if (current.dist > dist.get(current.id)) continue;
                if (current.id.equals(target)) break;
                ////PODA: si ya se alcanzó el destino, se detiene el algoritmo

                for (Edge edge : adj.getOrDefault(current.id, List.of())) {
                    double newDist = current.dist + edge.weight;
                    if (newDist < dist.get(edge.to)) {
                        dist.put(edge.to, newDist);
                        prev.put(edge.to, current.id);
                        pq.add(new Node(edge.to, newDist));
                    }
                }
            }

            // reconstruir camino
            List<String> path = new ArrayList<>();
            String curr = target;
            if (!prev.containsKey(curr) && !curr.equals(source)) {
                return new DijkstraResult(Collections.emptyList(), Double.POSITIVE_INFINITY);
            }
            while (curr != null) {
                path.add(curr);
                curr = prev.get(curr);
            }
            Collections.reverse(path);
            return new DijkstraResult(path, dist.getOrDefault(target, Double.POSITIVE_INFINITY));
        }

        static class Edge {
            String to;
            double weight;

            Edge(String to, double weight) {
                this.to = to;
                this.weight = weight;
            }
        }

        static class Node {
            String id;
            double dist;

            Node(String id, double dist) {
                this.id = id;
                this.dist = dist;
            }
        }

        static class DijkstraResult {
            private final List<String> path;
            private final double cost;

            public DijkstraResult(List<String> path, double cost) {
                this.path = path;
                this.cost = cost;
            }

            public List<String> getPath() {
                return path;
            }

            public double getCost() {
                return cost;
            }
        }
    }
}
