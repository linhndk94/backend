package com.framework.backend.dto.detail_dto;

import com.framework.backend.dto.simple_dto.DummySimpleDto;
import com.framework.backend.entities.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class DummyDetailDto extends DummySimpleDto {
    private String username;
    private String password;
    private List<Role> roles;
}
