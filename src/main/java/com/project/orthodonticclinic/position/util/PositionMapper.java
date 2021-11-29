package com.project.orthodonticclinic.position.util;

import com.project.orthodonticclinic.position.Position;
import com.project.orthodonticclinic.position.payload.PositionResponse;

public interface PositionMapper {

    PositionResponse mapToPositionResponse(Position position);
}
