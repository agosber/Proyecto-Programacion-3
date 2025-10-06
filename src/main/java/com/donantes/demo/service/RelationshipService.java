package com.donantes.demo.service;

public interface RelationshipService {
    public void createCompatibility(String donorId, String patientId);
    public void assignHospital(String patientId, String hospitalId);
}
