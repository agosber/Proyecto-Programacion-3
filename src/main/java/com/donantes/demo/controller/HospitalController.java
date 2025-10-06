package com.donantes.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donantes.demo.entity.Hospital;
import com.donantes.demo.repository.HospitalRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
public class HospitalController {
    
    private final HospitalRepository hospitalRepository;

    @PostMapping
    public Hospital createHospital(@RequestBody Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    @GetMapping
    public List<Hospital> listHospitals() {
        return hospitalRepository.findAll();
    }

    @GetMapping("/{id}")
    public Hospital getHospitalById(@PathVariable String id) {
        return hospitalRepository.findById(id).orElse(null);
    }
    @DeleteMapping("/{id}")
    public void deleteHospital(@PathVariable String id) {
        hospitalRepository.deleteById(id);
    }
}
