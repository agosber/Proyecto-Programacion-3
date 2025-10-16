package com.donantes.demo.controller;

import com.donantes.demo.service.PriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/matching")
@RequiredArgsConstructor
public class PriorityController {

    private final PriorityService priorityService;

    @GetMapping("/assign")
    public List<Map<String, Object>> assignDonors() {
        return priorityService.assignDonorsToPatients();
    }
}
