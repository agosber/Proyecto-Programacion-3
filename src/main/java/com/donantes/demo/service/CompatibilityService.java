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

    // DLS: encuentra donantes compatibles hasta cierta profundidad, solo si están disponibles
    public List<Donor> findCompatibleDonorsDLS(String patientId, int limit) {
        Optional<Patient> optPatient = patientRepository.findById(patientId);
        if (optPatient.isEmpty()) return new ArrayList<>();

        List<Donor> result = new ArrayList<>();
        depthLimitedSearch(optPatient.get(), limit, 0, result, new HashSet<>());
        return result;
    }

    private void depthLimitedSearch(Patient patient, int limit, int depth, List<Donor> result, Set<String> visited) {
        if (depth > limit || patient.getDonantes() == null) return; // poda por profundidad

        for (Donor donor : patient.getDonantes()) {
            if (!visited.contains(donor.getId())) {
                visited.add(donor.getId());

                // Solo agregamos si es compatible y está disponible
                if (isMedicallyCompatible(patient, donor) && donor.isDisponibilidad()) {
                    result.add(donor);
                }
            }
        }
    }

    private boolean isMedicallyCompatible(Patient patient, Donor donor) {
        if (!isBloodCompatible(donor.getTipoSangre(), patient.getTipoSangre())) {
            return false;
        }
        for (String organo : patient.getOrganosNecesarios()) {
            if (donor.getOrganos().contains(organo)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBloodCompatible(String donorBlood, String patientBlood) {
        Map<String, Set<String>> compatibleMap = new HashMap<>();
        compatibleMap.put("O-", Set.of("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+"));
        compatibleMap.put("O+", Set.of("O+", "A+", "B+", "AB+"));
        compatibleMap.put("A-", Set.of("A-", "A+", "AB-", "AB+"));
        compatibleMap.put("A+", Set.of("A+", "AB+"));
        compatibleMap.put("B-", Set.of("B-", "B+", "AB-", "AB+"));
        compatibleMap.put("B+", Set.of("B+", "AB+"));
        compatibleMap.put("AB-", Set.of("AB-", "AB+"));
        compatibleMap.put("AB+", Set.of("AB+"));

        return compatibleMap.getOrDefault(donorBlood, Set.of()).contains(patientBlood);
    }
}
