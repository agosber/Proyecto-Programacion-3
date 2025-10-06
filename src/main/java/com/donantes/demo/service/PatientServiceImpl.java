package com.donantes.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donantes.demo.entity.Patient;
import com.donantes.demo.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService{

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient save(Patient patient) {
       return patientRepository.save(patient);
    }

    @Override
    public List<Patient> list() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getById(String id) {
        return patientRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        patientRepository.deleteById(id);
    }

}
