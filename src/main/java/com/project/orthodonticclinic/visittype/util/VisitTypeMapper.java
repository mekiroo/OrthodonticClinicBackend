package com.project.orthodonticclinic.visittype.util;

import com.project.orthodonticclinic.visittype.VisitType;
import com.project.orthodonticclinic.visittype.payload.VisitTypeResponse;

public interface VisitTypeMapper {

    VisitTypeResponse mapToVisitTypeResponse(VisitType visitType);
}
