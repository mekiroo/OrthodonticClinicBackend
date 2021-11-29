package com.project.orthodonticclinic.visittype.util;

import com.project.orthodonticclinic.visittype.VisitType;
import com.project.orthodonticclinic.visittype.payload.VisitTypeResponse;
import org.springframework.stereotype.Component;

@Component
public class VisitTypeMapperImpl implements VisitTypeMapper {

    @Override
    public VisitTypeResponse mapToVisitTypeResponse(VisitType visitType) {
        return VisitTypeResponse.builder()
                .id(visitType.getId())
                .name(visitType.getName())
                .description(visitType.getDescription())
                .duration(visitType.getDuration())
                .price(visitType.getPrice())
                .patientCanBook(visitType.isPatientCanBook())
                .positionId(visitType.getPosition().getId())
                .build();
    }
}
