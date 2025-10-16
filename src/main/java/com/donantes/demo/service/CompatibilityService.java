package com.donantes.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donantes.demo.entity.Donor;
import com.donantes.demo.entity.Patient;
import com.donantes.demo.repository.PatientRepository;

@Service
public class CompatibilityService {

    @Autowired
    private PatientRepository patientRepository;

    //se usa BFS para buscar donantes compatibles recorriendo los nodos por niveles (más cercanos primero).
    // y usa una cola para asegurar el recorrido nivel a nivel.
    public List<Donor> findCompatibleDonorsBFS(String patientId) {
        Optional<Patient> optPatient = patientRepository.findById(patientId);
        if (optPatient.isEmpty()) return new ArrayList<>();

        Patient patient = optPatient.get();
        List<Donor> result = new ArrayList<>();
        Queue<Donor> queue = new LinkedList<>();

        if (patient.getDonantes() != null) {
            queue.addAll(patient.getDonantes());
        }

        Set<String> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            Donor donor = queue.poll();
            if (!visited.contains(donor.getId())) {
                visited.add(donor.getId());
                result.add(donor);
            }
        }
        return result;
    }

    //DLS sirve acá para encontrar donantes compatibles hasta cierta “distancia” en el grafo.
    //Usa recursión y un parámetro de profundidad límite para evitar ir infinito.
    public List<Donor> findCompatibleDonorsDLS(String patientId, int limit) {
        Optional<Patient> optPatient = patientRepository.findById(patientId);
        if (optPatient.isEmpty()) return new ArrayList<>();
        List<Donor> result = new ArrayList<>();
        depthLimitedSearch(optPatient.get(), limit, 0, result, new HashSet<>());
        return result;
    }

    private void depthLimitedSearch(Patient patient, int limit, int depth, List<Donor> result, Set<String> visited) {
        if (depth > limit || patient.getDonantes() == null) return;//poda por profundidad
        for (Donor donor : patient.getDonantes()) {
            if (!visited.contains(donor.getId())) {
                visited.add(donor.getId());
                result.add(donor);
            }
        }
    }
}