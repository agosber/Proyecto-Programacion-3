package com.donantes.demo.service;

import java.util.List;
import java.util.Optional;

import com.donantes.demo.entity.Patient;

public interface PatientService {
    public Patient save(Patient patient);
    public List <Patient> list();
    public Optional<Patient> getById(String id);
    public void delete(String id);
}
