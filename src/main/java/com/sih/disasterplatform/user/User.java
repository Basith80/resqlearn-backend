package com.sih.disasterplatform.user;

import com.sih.disasterplatform.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String username;
    private String password;

    private Long schoolId;

    private Role role;
}
