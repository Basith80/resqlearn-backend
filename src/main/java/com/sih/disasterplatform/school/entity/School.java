package com.sih.disasterplatform.school.entity;

import com.sih.disasterplatform.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "schools")
public class School extends BaseEntity {

    private String name;
}
