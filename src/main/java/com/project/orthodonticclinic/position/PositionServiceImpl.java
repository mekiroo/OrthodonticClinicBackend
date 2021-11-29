package com.project.orthodonticclinic.position;

import com.project.orthodonticclinic.exception.ApplicationException;
import com.project.orthodonticclinic.exception.Error;
import com.project.orthodonticclinic.position.payload.PositionResponse;
import com.project.orthodonticclinic.position.util.PositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    @Override
    public PositionResponse getPositionById(Long positionId) {
        var position = getPositionEntityById(positionId);
        return positionMapper.mapToPositionResponse(position);
    }

    private Position getPositionEntityById(Long positionId) {
        return positionRepository.findById(positionId)
                .orElseThrow(() -> new ApplicationException(Error.INCORRECT_POSITION_ID));
    }

    @Override
    public List<PositionResponse> getAllPositions() {
        return positionRepository.findAll().stream()
                .map(positionMapper::mapToPositionResponse)
                .collect(Collectors.toList());
    }
}
