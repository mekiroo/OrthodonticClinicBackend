package com.project.orthodonticclinic.visit.visitstatus;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "visit_statuses")
public class VisitStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private EVisitStatusName name;
}
