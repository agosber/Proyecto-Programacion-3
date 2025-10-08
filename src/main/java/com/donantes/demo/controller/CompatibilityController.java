package com.donantes.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.donantes.demo.entity.Donor;
import com.donantes.demo.service.CompatibilityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/compatibility")
@RequiredArgsConstructor
public class CompatibilityController {

    private final CompatibilityService compatibilityService;

    @GetMapping("/bfs")
    public List<Donor> getCompatibleDonorsBFS(@RequestParam String patientId) {
        return compatibilityService.findCompatibleDonorsBFS(patientId);
    }

    @GetMapping("/dls")
    public List<Donor> getCompatibleDonorsDLS(@RequestParam String patientId,
                                               @RequestParam int limit) {
        return compatibilityService.findCompatibleDonorsDLS(patientId, limit);
    }
}
