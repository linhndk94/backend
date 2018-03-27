package com.framework.backend.dto.simple_dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DummySimpleDto extends BaseSimpleDto {
    private String name;
}
