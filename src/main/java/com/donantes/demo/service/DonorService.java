package com.donantes.demo.service;

import java.util.List;
import java.util.Optional;

import com.donantes.demo.entity.Donor;

public interface DonorService {
    public List<Donor> list();
    public Optional<Donor> getById(String id);
    public Donor save(Donor donor);
    public void delete(String id);
}

