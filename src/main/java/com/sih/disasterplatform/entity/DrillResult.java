package com.sih.disasterplatform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drill_results")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DrillResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "drill_id", nullable = false)
    private VirtualDrill drill;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = true) // null if Individual user
    private Student student;

    // Store email for Individual users who have no Student profile
    @Column
    private String userEmail;

    @Column(nullable = false)
    private String selectedOption; // "A", "B", "C", or "D"

    @Column(nullable = false)
    private boolean correct;

    // Did they answer within time limit?
    @Column(nullable = false)
    private boolean answeredInTime;
}