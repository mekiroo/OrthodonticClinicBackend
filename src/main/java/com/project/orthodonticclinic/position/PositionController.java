package com.project.orthodonticclinic.position;

import com.project.orthodonticclinic.position.payload.PositionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping("/{id}")
    public PositionResponse getPositionById(@PathVariable Long id) {
        return positionService.getPositionById(id);
    }

    @GetMapping
    public List<PositionResponse> getAllPositions() {
        return positionService.getAllPositions();
    }
}
