package com.framework.backend.dto.create_dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DummyCreateDto extends BaseCreateDto {
    private String name;
    private String username;
    private String password;
}
