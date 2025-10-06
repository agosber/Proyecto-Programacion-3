package com.donantes.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donantes.demo.entity.Donor;
import com.donantes.demo.repository.DonorRepository;
import com.donantes.demo.service.DonorService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/donors")
@RequiredArgsConstructor
public class DonorController {

    private final DonorService donorService;

    @PostMapping
    public Donor createDonante(@RequestBody Donor donor) {
        return donorService.save(donor);
    }

    @GetMapping
    public List<Donor> listDonors(){
        return donorService.list();
    }

    @GetMapping("/{id}")
    public Donor getDonorById(@PathVariable String id){
        return donorService.getById(id).orElse(null);
    }
    @DeleteMapping("/{id}")
    public void deleteDonor(@PathVariable String id){
        donorService.delete(id);
    }

}
