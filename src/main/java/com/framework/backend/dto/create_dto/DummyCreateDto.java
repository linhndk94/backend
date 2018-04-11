package com.framework.backend.dto.create_dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DummyCreateDto extends BaseCreateDto {
    private String name;
    private Integer phoneNumber;
}
