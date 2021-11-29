package com.project.orthodonticclinic.user.patient;

import com.project.orthodonticclinic.user.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "patients")
public class Patient extends User {

    private String pesel;
}
