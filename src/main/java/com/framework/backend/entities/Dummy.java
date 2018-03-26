package com.framework.backend.entities;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Dummy extends BaseEntity {

    private String name;
}
