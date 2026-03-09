package com.sih.disasterplatform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "virtual_drills")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class VirtualDrill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String scenario; // e.g. "Fire breaks out in classroom - what do you do?"

    @Column(nullable = false)
    private String optionA;

    @Column(nullable = false)
    private String optionB;

    @Column(nullable = false)
    private String optionC;

    @Column(nullable = false)
    private String optionD;

    @Column(nullable = false)
    private String correctOption; // "A", "B", "C", or "D"

    @Column(nullable = false)
    private int timeLimitSeconds; // e.g. 30 seconds per question

    @ManyToOne
    @JoinColumn(name = "disaster_id", nullable = false)
    private Disaster disaster;
}