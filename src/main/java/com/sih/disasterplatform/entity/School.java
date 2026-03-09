package com.sih.disasterplatform.entity;

import jakarta.persistence.*;
import lombok.*;
@Data
@Entity
@Table(name = "schools")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    private String district;

    private String contactEmail;

    // ✅ Secret code for teachers to join this school
    @Column(nullable = false, unique = true)
    private String teacherCode;

    // ✅ Secret code for students to join this school
    @Column(nullable = false, unique = true)
    private String studentCode;
}