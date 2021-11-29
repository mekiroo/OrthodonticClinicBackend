package com.project.orthodonticclinic.visittype;

import com.project.orthodonticclinic.visittype.payload.VisitTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/visit-types")
public class VisitTypeController {

    private final VisitTypeService visitTypeService;

    @GetMapping("/{id}")
    public VisitTypeResponse getVisitTypeById(@PathVariable Long id) {
        return visitTypeService.getVisitTypeById(id);
    }

    @GetMapping()
    public List<VisitTypeResponse> getVisitTypes() {
        return visitTypeService.getAllVisitTypes();
    }
}
