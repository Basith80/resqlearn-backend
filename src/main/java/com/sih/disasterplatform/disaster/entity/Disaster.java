package com.sih.disasterplatform.disaster.entity;

import com.sih.disasterplatform.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "disasters")
public class Disaster extends BaseEntity {

    private String name;
    private String description;
}
