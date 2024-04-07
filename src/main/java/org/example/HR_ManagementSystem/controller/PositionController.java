package org.example.HR_ManagementSystem.controller;

import org.example.HR_ManagementSystem.collection.data.PositionDataProcessing;
import org.example.HR_ManagementSystem.collection.entity.Position;
import org.example.HR_ManagementSystem.controller.request.PositionRequest;
import org.example.HR_ManagementSystem.dto.PositionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/positions")
public class PositionController {
    private final PositionDataProcessing positionDataProcessing;

    @Autowired
    public PositionController(PositionDataProcessing positionDataProcessing) {
        this.positionDataProcessing = positionDataProcessing;
    }

    @PostMapping
    public ResponseEntity<PositionDTO> createPosition(@RequestParam PositionRequest positionRequest) {
        PositionDTO createPosition = positionDataProcessing.create(positionRequest.getName());
        return ResponseEntity.ok(createPosition);
    }

    @PutMapping
    public ResponseEntity<PositionDTO> modifyPosition(@RequestBody PositionRequest positionRequest) {
      PositionDTO position = positionDataProcessing.modify(positionRequest);
        return ResponseEntity.ok(position);
    }

    @DeleteMapping(value = "/{id}")
    public void deletePosition(@PathVariable("id")  int id) {
        positionDataProcessing.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionDTO> getPosition(@PathVariable("id") int id) {
        PositionDTO positionDTO = positionDataProcessing.find(id);
        return ResponseEntity.ok(positionDTO);
    }

    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions() {
        List<Position> positions = positionDataProcessing.printAll();
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<PositionDTO>> getPositionsEmployees() {
        List<PositionDTO> positionDTOs = positionDataProcessing.printPositionsEmployees();
        return ResponseEntity.ok(positionDTOs);
    }
}
