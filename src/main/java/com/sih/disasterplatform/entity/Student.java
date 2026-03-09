package com.sih.disasterplatform.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "students")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = true)
    private School school;

    // ✅ grade removed — not needed anymore
}