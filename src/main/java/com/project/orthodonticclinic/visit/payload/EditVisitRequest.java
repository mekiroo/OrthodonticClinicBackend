package com.project.orthodonticclinic.visit.payload;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class EditVisitRequest {

    @NotNull(message = "visitStatusId id can not be blank")
    private String visitStatus;
    @NotBlank(message = "description can not be blank")
    private String description;
}
