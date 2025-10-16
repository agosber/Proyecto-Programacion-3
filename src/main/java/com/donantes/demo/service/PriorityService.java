package com.donantes.demo.service;

import com.donantes.demo.entity.Donor;
import com.donantes.demo.entity.Patient;
import com.donantes.demo.repository.DonorRepository;
import com.donantes.demo.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class PriorityService {

    private final PatientRepository patientRepository;
    private final DonorRepository donorRepository;
    private final CompatibilityService compatibilityService;
    private final Neo4jClient neo4jClient;

//Algoritmos combinados:
     //1. MergeSort → Divide y vencerás: ordena los pacientes por prioridad (1 = mayor prioridad).
     //2. BFS → usado desde CompatibilityService para obtener donantes compatibles.
     // 3. Greedy → selecciona el primer donante disponible para cada paciente.

    @Transactional
    public List<Map<String, Object>> assignDonorsToPatients() {
        // Obtener todos los pacientes
        List<Patient> pacientes = patientRepository.findAll();

        // Ordenar pacientes por prioridad usando MergeSort (1 = mayor prioridad)
        pacientes = mergeSortByPriority(pacientes);

        List<Map<String, Object>> resultados = new ArrayList<>();

        for (Patient paciente : pacientes) {
    // Obtener donantes compatibles usando DLS (profundidad límite, por ejemplo 3)
    List<Donor> compatibles = compatibilityService.findCompatibleDonorsDLS(paciente.getId(), 3);

    // Algoritmo greedy: tomar el primer donante disponible
    Optional<Donor> donanteAsignado = compatibles.stream()
            .filter(Donor::isDisponibilidad)
            .findFirst();

    Map<String, Object> info = new HashMap<>();
    info.put("paciente", paciente.getNombre());
    info.put("prioridad", paciente.getPrioridad());

    if (donanteAsignado.isPresent()) {
        Donor donante = donanteAsignado.get();

        // Crear relación ASIGNADO_A en Neo4j y marcar como no disponible
        String query = """
            MATCH (d:Donante {id: $donorId}), (p:Paciente {id: $patientId})
            MERGE (d)-[:ASIGNADO_A]->(p)
            SET d.disponibilidad = false
        """;
        neo4jClient.query(query)
                .bind(donante.getId()).to("donorId")
                .bind(paciente.getId()).to("patientId")
                .run();

        // Actualizar disponibilidad en memoria y repositorio
        donante.setDisponibilidad(false);
        donorRepository.save(donante);

        info.put("donante_asignado", donante.getNombre());
    } else {
        info.put("donante_asignado", "Ninguno disponible");
    }

    resultados.add(info);
}


        return resultados;
    }

    //  MergeSort

    private List<Patient> mergeSortByPriority(List<Patient> pacientes) {
        if (pacientes.size() <= 1) return pacientes;

        int mid = pacientes.size() / 2;
        List<Patient> left = mergeSortByPriority(new ArrayList<>(pacientes.subList(0, mid)));
        List<Patient> right = mergeSortByPriority(new ArrayList<>(pacientes.subList(mid, pacientes.size())));

        return merge(left, right);
    }

    private List<Patient> merge(List<Patient> left, List<Patient> right) {
        List<Patient> sorted = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getPrioridad() < right.get(j).getPrioridad()) { // 1 = mayor prioridad
                sorted.add(left.get(i++));
            } else {
                sorted.add(right.get(j++));
            }
        }

        while (i < left.size()) sorted.add(left.get(i++));
        while (j < right.size()) sorted.add(right.get(j++));

        return sorted;
    }
}
