package org.example.HR_ManagementSystem.controllers.controllerRest;

import org.example.HR_ManagementSystem.model.request.PositionRequest;
import org.example.HR_ManagementSystem.service.PositionService;
import org.example.HR_ManagementSystem.source.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/positions")
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @PostMapping
    public Position createPosition(@RequestBody PositionRequest positionRequest) {
        return positionService.create(positionRequest);
    }

    @PutMapping("/{positionId}")
    public Position updatePosition(@PathVariable Long positionId, @RequestBody PositionRequest positionRequest) {
        return positionService.update(positionId, positionRequest);
    }

    @DeleteMapping("/{positionId}")
    public void deletePosition(@PathVariable Long positionId) {
        positionService.delete(positionId);
    }

    @GetMapping("/{positionId}")
    public Position getPositionById(@PathVariable Long positionId) {
        return positionService.getPositionById(positionId);
    }

    @GetMapping
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }
}
