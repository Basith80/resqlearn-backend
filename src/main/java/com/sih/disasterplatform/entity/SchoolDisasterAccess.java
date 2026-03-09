package com.sih.disasterplatform.entity;

import jakarta.persistence.*;
import lombok.*;
@Data
@Entity
@Table(name = "school_disaster_access",
        uniqueConstraints = @UniqueConstraint(columnNames = {"school_id", "disaster_id"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SchoolDisasterAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @ManyToOne
    @JoinColumn(name = "disaster_id", nullable = false)
    private Disaster disaster;

    // ✅ Teacher toggles this true/false
    @Column(nullable = false)
    private boolean enabled = false;
}