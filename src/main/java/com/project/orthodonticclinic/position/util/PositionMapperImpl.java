package com.project.orthodonticclinic.position.util;

import com.project.orthodonticclinic.position.Position;
import com.project.orthodonticclinic.position.payload.PositionResponse;
import org.springframework.stereotype.Component;

@Component
public class PositionMapperImpl implements PositionMapper {

    @Override
    public PositionResponse mapToPositionResponse(Position position) {
        return PositionResponse.builder()
                .id(position.getId())
                .name(position.getName())
                .build();
    }
}
