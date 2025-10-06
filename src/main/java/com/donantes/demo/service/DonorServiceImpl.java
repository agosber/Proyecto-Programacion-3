package com.donantes.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donantes.demo.entity.Donor;
import com.donantes.demo.repository.DonorRepository;

@Service
public class DonorServiceImpl implements DonorService{

    @Autowired
    private DonorRepository donorRepository;

    @Override
    public List<Donor> list(){
        return donorRepository.findAll();
    }
    @Override
    public Optional<Donor> getById(String id){
        return donorRepository.findById(id);
    }
    @Override
    public Donor save(Donor donor){
        if (donor.getId() == null || donor.getId().isEmpty()) {
        throw new IllegalArgumentException("El Donor debe tener un ID");
    }
    if (donor.getTipoSangre() == null || donor.getTipoSangre().isEmpty()) {
        throw new IllegalArgumentException("El tipo de sangre es obligatorio");
    }
        return donorRepository.save(donor);
    }
    @Override
    public void delete(String id){
        donorRepository.deleteById(id);
    }
}
