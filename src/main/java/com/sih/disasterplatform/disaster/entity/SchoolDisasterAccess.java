package com.sih.disasterplatform.disaster.entity;

import com.sih.disasterplatform.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "school_disaster_access")
public class SchoolDisasterAccess extends BaseEntity {

    private Long schoolId;
    private Long disasterId;
    private boolean unlocked;
}
