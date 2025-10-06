package com.donantes.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donantes.demo.repository.RelationshipRepository;

@Service
public class RelationshipServiceImpl implements RelationshipService{

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Override
    public void createCompatibility(String donorId, String patientId) {
        relationshipRepository.createCompatibility(donorId, patientId);
    }

    @Override
    public void assignHospital(String patientId, String hospitalId) {
        relationshipRepository.assignHospital(patientId, hospitalId);
    }


}
