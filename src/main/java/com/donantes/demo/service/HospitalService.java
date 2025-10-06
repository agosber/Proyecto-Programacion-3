package com.donantes.demo.service;

import java.util.List;
import java.util.Optional;

import com.donantes.demo.entity.Hospital;

public interface HospitalService {
    public Hospital save(Hospital hospital);
    public List <Hospital> list();
    public Optional<Hospital> getById(String id);
    public void delete(String id);
}
