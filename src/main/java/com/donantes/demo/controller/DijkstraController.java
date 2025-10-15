package com.donantes.demo.controller;

import com.donantes.demo.service.DijkstraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rutas")
public class DijkstraController {

    private final DijkstraService dijkstraService;

    public DijkstraController(DijkstraService dijkstraService) {
        this.dijkstraService = dijkstraService;
    }

    @GetMapping("/dijkstra")
    public ResponseEntity<?> shortestPath(
            @RequestParam String from,
            @RequestParam String to
    ) {
        Map<String, Object> result = dijkstraService.shortestPath(from, to);
        return ResponseEntity.ok(result);
    }
}
