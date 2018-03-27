package com.framework.backend.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Dummy extends BaseEntity {
    @Nationalized
    private String name;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
}
