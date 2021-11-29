package com.project.orthodonticclinic.visittype;

import com.project.orthodonticclinic.position.Position;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "visit_types")
public class VisitType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Long duration;
    private BigDecimal price;
    @Column(name = "patient_can_book")
    private boolean patientCanBook;
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
}
