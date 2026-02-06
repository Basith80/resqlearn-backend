package com.sih.disasterplatform.disaster.entity;

import com.sih.disasterplatform.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "disaster_education_progress")
public class DisasterEducationProgress extends BaseEntity {

    private Long userId;
    private Long disasterId;
    private boolean completed;
}
