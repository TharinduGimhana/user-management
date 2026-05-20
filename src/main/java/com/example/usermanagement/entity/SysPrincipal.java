package com.example.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sys_principal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysPrincipal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_id")
    private Long subjectId;

    // Relationships

    @OneToMany(mappedBy = "principal", cascade = CascadeType.ALL)
    private List<SysSubject> subjects = new ArrayList<>();
}

