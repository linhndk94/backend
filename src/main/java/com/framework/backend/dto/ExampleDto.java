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

    public Example<T> toExample() {
        ExampleMatcher exampleMatcher = isMatchingAny ? ExampleMatcher.matchingAny() : ExampleMatcher.matchingAll();
        exampleMatcher = isNullIncluded ? exampleMatcher.withIncludeNullValues() : exampleMatcher.withIgnoreNullValues();
        exampleMatcher = isIgnoreCase ? exampleMatcher.withIgnoreCase() : exampleMatcher;
        if (mappingProperties != null && !mappingProperties.isEmpty()) {
            for (Map.Entry<String, String> entry : mappingProperties.entrySet()) {
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
        if (ignoreCaseProperties != null && !ignoreCaseProperties.isEmpty()) {
            exampleMatcher = exampleMatcher.withIgnoreCase(ignoreCaseProperties.toArray(new String[ignoreCaseProperties.size()]));
        }
        if (ignoreProperties != null && !ignoreProperties.isEmpty()) {
            exampleMatcher = exampleMatcher.withIgnorePaths(ignoreProperties.toArray(new String[ignoreProperties.size()]));
        }

        return Example.of(payload, exampleMatcher);
    }
}
