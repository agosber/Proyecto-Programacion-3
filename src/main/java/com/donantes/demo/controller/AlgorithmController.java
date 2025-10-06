package com.donantes.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.donantes.demo.algorithms.BFSService;
import com.donantes.demo.algorithms.DijkstraService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/algorithms")
@RequiredArgsConstructor
public class AlgorithmController {

    private final BFSService bfsService;
    private final DijkstraService dijkstraService;

    @GetMapping("/bfs")
    public List<String> bfs(@RequestParam String patientId, @RequestParam(defaultValue = "2") int depth) {
        return bfsService.findNearbyDonors(patientId, depth);
    }
    @GetMapping("/dijkstra")
    public Map<String, Object> dijkstra(
        @RequestParam String donorId,
        @RequestParam String hospitalId) {
    return dijkstraService.shortestPath(donorId, hospitalId);
}

}
