package com.donantes.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.donantes.demo.repository.RelationshipRepository;
import com.donantes.demo.service.RelationshipService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/relationships")
@RequiredArgsConstructor
public class RelationshipController {

  
    private final RelationshipService relationshipService;

    @PostMapping("/compatible")
    public ResponseEntity<String> createCompatibility(
            @RequestParam String donorId,
            @RequestParam String patientId) {

        relationshipService.createCompatibility(donorId, patientId);
        return ResponseEntity.status(201)
                .body("Relación COMPATIBLE_CON creada entre Donante " + donorId + " y Paciente " + patientId);
    }

    @PostMapping("/located")
    public ResponseEntity<String> assignHospital(
            @RequestParam String personaId,
            @RequestParam String hospitalId) {

        relationshipService.assignHospital(personaId, hospitalId);
        return ResponseEntity.status(201)
                .body("Persona " + personaId + " ubicada en Hospital " + hospitalId);
    }

    

}
