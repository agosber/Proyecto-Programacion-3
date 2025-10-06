package com.donantes.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donantes.demo.entity.Hospital;
import com.donantes.demo.repository.HospitalRepository;

@Service
public class HospitalServiceImpl implements HospitalService{

    @Autowired
    private HospitalRepository hospitalRepository;
    
    @Override
    public Hospital save(Hospital hospital) {
       return hospitalRepository.save(hospital);
    }

    @Override
    public List<Hospital> list() {
        return hospitalRepository.findAll();
    }

    @Override
    public Optional<Hospital> getById(String id) {
        return hospitalRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        hospitalRepository.deleteById(id);
    }

}
