package com.project.orthodonticclinic.visittype;

import com.project.orthodonticclinic.visittype.payload.VisitTypeResponse;

import java.util.List;

public interface VisitTypeService {

    VisitTypeResponse getVisitTypeById(Long visitTypeId);

    List<VisitTypeResponse> getAllVisitTypes();
}
