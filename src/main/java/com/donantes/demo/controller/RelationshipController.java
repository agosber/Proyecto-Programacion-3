package com.donantes.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.donantes.demo.repository.RelationshipRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/relationships")
@RequiredArgsConstructor
public class RelationshipController {

    private final RelationshipRepository relationshipRepository;

    @PostMapping("/compatible")
    public String createCompatibilities(@RequestParam String donanteId,
                                      @RequestParam String pacienteId) {
        relationshipRepository.createCompatibility(donanteId, pacienteId);
        return "Relación COMPATIBLE_CON creada entre Donante " + donanteId + " y Paciente " + pacienteId;
    }

    @PostMapping("/located")
    public String assignHospitals(@RequestParam String personaId,
                                  @RequestParam String hospitalId) {
        relationshipRepository.assignHospital(personaId, hospitalId);
        return "Persona " + personaId + " ubicada en Hospital " + hospitalId;
    }


    

}
