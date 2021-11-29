package com.project.orthodonticclinic.user.employee;

import com.project.orthodonticclinic.position.Position;
import com.project.orthodonticclinic.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "employees")
public class Employee extends User {
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
}
