package com.framework.backend.support;

import com.framework.backend.dto.ExampleDto;
import com.framework.backend.entities.BaseEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.Map;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

public class ExampleFactory {
     public static <T extends BaseEntity> Example<T> createExample(ExampleDto<T> exampleDto) {
        ExampleMatcher exampleMatcher = exampleDto.isMatchingAny() ? ExampleMatcher.matchingAny() : ExampleMatcher.matchingAll();
        exampleMatcher = exampleDto.isNullIncluded() ? exampleMatcher.withIncludeNullValues() : exampleMatcher.withIgnoreNullValues();
        exampleMatcher = exampleDto.isIgnoreCase() ? exampleMatcher.withIgnoreCase() : exampleMatcher;
        if (exampleDto.getMappingProperties() != null && !exampleDto.getMappingProperties().isEmpty()) {
            for (Map.Entry<String, String> entry : exampleDto.getMappingProperties().entrySet()) {
                switch (entry.getValue().toUpperCase()) {
                    case "EXACT":
                        exampleMatcher = exampleMatcher.withMatcher(entry.getKey(), exact());
                        break;
                    case "STARTING":
                        exampleMatcher = exampleMatcher.withMatcher(entry.getKey(), startsWith());
                        break;
                    case "ENDING":
                        exampleMatcher = exampleMatcher.withMatcher(entry.getKey(), endsWith());
                        break;
                    case "CONTAINING":
                        exampleMatcher = exampleMatcher.withMatcher(entry.getKey(), contains());
                        break;
                    case "REGEX":
                        exampleMatcher = exampleMatcher.withMatcher(entry.getKey(), regex());
                        break;
                    default:
                        exampleMatcher = exampleMatcher.withMatcher(entry.getKey(), storeDefaultMatching());
                        break;
                }
            }
        }
        if (exampleDto.getIgnoreCaseProperties() != null && !exampleDto.getIgnoreCaseProperties().isEmpty()) {
            exampleMatcher = exampleMatcher.withIgnoreCase(exampleDto.getIgnoreCaseProperties().toArray(new String[exampleDto.getIgnoreCaseProperties().size()]));
        }
        if (exampleDto.getIgnoreProperties() != null && !exampleDto.getIgnoreProperties().isEmpty()) {
            exampleMatcher = exampleMatcher.withIgnorePaths(exampleDto.getIgnoreProperties().toArray(new String[exampleDto.getIgnoreProperties().size()]));
        }

        return Example.of(exampleDto.getPayload(), exampleMatcher);
    }
}
