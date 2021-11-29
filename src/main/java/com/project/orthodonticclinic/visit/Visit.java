package com.project.orthodonticclinic.visit;

import com.project.orthodonticclinic.user.employee.Employee;
import com.project.orthodonticclinic.user.patient.Patient;
import com.project.orthodonticclinic.visit.visitstatus.VisitStatus;
import com.project.orthodonticclinic.visittype.VisitType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Data
@Table(name = "visits")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "visit_type_id")
    private VisitType visitType;
    @ManyToOne
    @JoinColumn(name = "visit_status_id")
    private VisitStatus visitStatus;
    private String description;
    private LocalDate date;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return Objects.equals(id, visit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
