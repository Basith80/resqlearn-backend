package com.sih.disasterplatform.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * One row = one education paragraph for a disaster.
 * content is a JSON string with fields:
 *   paragraph, question, options (array), answer
 *
 * Example JSON stored in content column:
 * {
 *   "paragraph": "Fire needs fuel, oxygen and heat...",
 *   "question":  "What are the three elements of the fire triangle?",
 *   "options":   ["Wood, Air, Water", "Fuel, Oxygen, Heat", ...],
 *   "answer":    "Fuel, Oxygen, Heat"
 * }
 *
 * displayOrder defines the reading sequence (1, 2, 3...).
 */
@Entity
@Table(name = "education_content")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EducationContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "disaster_id", nullable = false)
    private Disaster disaster;

    /** JSON string containing paragraph text, question, options[], and answer */
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    /** Sort order (1 = first paragraph shown) */
    @Column(nullable = false)
    private int displayOrder;
}