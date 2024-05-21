package org.example.HR_ManagementSystem.controllers;

import org.example.HR_ManagementSystem.dto.PositionDto;
import org.example.HR_ManagementSystem.model.request.PositionRequest;
import org.example.HR_ManagementSystem.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping(value = "api/positions")
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/")
    public String WebController(Model model) {
        var position = positionService.read();
        model.addAttribute("positions", position);
        return "position";
    }

    @PostMapping
    public ResponseEntity<PositionDto> create(@RequestBody PositionRequest request) {
        return ResponseEntity.ok(positionService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<PositionDto>> read() {
        List<PositionDto> positionDtos = positionService.read();
        return ResponseEntity.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:8080/api/positions")
                .header("Access-Control-Allow-Methods", "GET")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .body(positionDtos);
    }

    @PutMapping
    public ResponseEntity<PositionDto> update(@RequestBody PositionRequest request) {
        PositionDto updatePosition = positionService.update(request);
        return ResponseEntity.ok(updatePosition);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        positionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionDto> get(@PathVariable Long id) {
        PositionDto findPosition = positionService.findById(id);
        return ResponseEntity.ok(findPosition);
    }

    @GetMapping("/with-employees")
    public ResponseEntity<List<PositionDto>> getPositionsWithEmployees() {
        List<PositionDto> positionsWithEmployees = positionService.getPositionsWithEmployees();
        return ResponseEntity.ok(positionsWithEmployees);
    }
}

