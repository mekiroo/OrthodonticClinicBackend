package com.project.orthodonticclinic.position;

import com.project.orthodonticclinic.position.payload.PositionResponse;

import java.util.List;

public interface PositionService {

    PositionResponse getPositionById(Long positionId);

    List<PositionResponse> getAllPositions();
}
