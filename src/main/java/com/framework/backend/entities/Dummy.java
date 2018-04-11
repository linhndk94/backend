package com.framework.backend.entities;

import lombok.*;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class Dummy extends BaseEntity {
    private String name;
    private String phoneNumber;
}
