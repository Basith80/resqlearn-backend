package com.sih.disasterplatform.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(
        name = "assessment_attempt",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "disaster_id"})
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AssessmentAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disaster_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Disaster disaster;

    @Column(nullable = false)
    private int attemptCount = 0;

    @Column(nullable = false)
    private int failCount = 0;

    @Column(nullable = false)
    private int cancelCount = 0;

    @Column(nullable = false)
    private int currentDenominator = 25;

    @Column
    private Integer leaderboardRawScore;

    @Column
    private Integer leaderboardScore;

    @Column
    private Integer leaderboardDenominator;

    @Column
    private LocalDateTime firstAttemptedAt;

    @Column
    private Integer lastRawScore;

    @Column
    private Integer lastEffectiveScore;

    @Column
    private Integer lastDenominator;
}