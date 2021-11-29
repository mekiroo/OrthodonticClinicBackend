package com.project.orthodonticclinic.visittype;

import com.project.orthodonticclinic.exception.ApplicationException;
import com.project.orthodonticclinic.exception.Error;
import com.project.orthodonticclinic.visittype.payload.VisitTypeResponse;
import com.project.orthodonticclinic.visittype.util.VisitTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitTypeServiceImpl implements VisitTypeService {

    private final VisitTypeRepository visitTypeRepository;
    private final VisitTypeMapper visitTypeMapper;

    @Override
    public VisitTypeResponse getVisitTypeById(Long visitTypeId) {
        var visitType = getVisitTypeEntityById(visitTypeId);
        return visitTypeMapper.mapToVisitTypeResponse(visitType);
    }

    @Override
    public List<VisitTypeResponse> getAllVisitTypes() {
        return visitTypeRepository.findAll().stream()
                .map(visitTypeMapper::mapToVisitTypeResponse)
                .collect(Collectors.toList());
    }

    private VisitType getVisitTypeEntityById(Long visitTypeId) {
        return visitTypeRepository.findById(visitTypeId)
                .orElseThrow(() -> new ApplicationException(Error.INCORRECT_VISIT_TYPE_ID));
    }
}
