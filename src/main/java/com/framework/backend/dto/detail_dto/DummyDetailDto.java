package com.framework.backend.dto.detail_dto;

import com.framework.backend.dto.simple_dto.DummySimpleDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DummyDetailDto extends DummySimpleDto {
    private String phoneNumber;
}
