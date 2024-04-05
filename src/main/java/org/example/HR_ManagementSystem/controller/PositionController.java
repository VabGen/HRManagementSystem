package org.example.HR_ManagementSystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.HR_ManagementSystem.collection.data.PositionDataProcessing;
import org.example.HR_ManagementSystem.collection.entity.Position;
import org.example.HR_ManagementSystem.controller.request.PositionRequest;
import org.example.HR_ManagementSystem.dto.PositionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/positions")
public class PositionController {
    private final PositionDataProcessing positionDataProcessing;

    public PositionController() {
        this.positionDataProcessing = new PositionDataProcessing();
    }

    @PostMapping
    public ResponseEntity<PositionDTO> createPosition(@RequestParam PositionRequest positionRequest) {
        PositionDTO createPosition = positionDataProcessing.createPosition(positionRequest.getName());
        return ResponseEntity.ok(createPosition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modifyPosition(@PathVariable int id, @RequestParam String newName) {
        positionDataProcessing.modifyPosition(id, newName);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable int id) {
        positionDataProcessing.deletePosition(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionDTO> getPosition(@PathVariable int id) {
        PositionDTO positionDTO = positionDataProcessing.printPosition(id);
        return ResponseEntity.ok(positionDTO);
    }

    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions() throws JsonProcessingException {
        List<Position> positions = positionDataProcessing.printAllPositions();
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<PositionDTO>> getPositionsEmployees() {
        List<PositionDTO> positionDTOs = positionDataProcessing.printPositionsEmployees();
        return ResponseEntity.ok(positionDTOs);
    }
}
