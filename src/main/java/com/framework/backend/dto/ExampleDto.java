package com.framework.backend.dto;

import com.framework.backend.entities.BaseEntity;
import lombok.Data;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

@Data
public class ExampleDto<T extends BaseEntity> {

    private T payload;

    private boolean isMatchingAny;
    private boolean isNullIncluded;
    private boolean isIgnoreCase;

    private Map<String, String> mappingProperties;
    private List<String> ignoreProperties;
    private List<String> ignoreCaseProperties;
}
