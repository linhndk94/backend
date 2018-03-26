package com.framework.backend.configuration;

import com.framework.backend.annotations.CustomRestController;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

@Configuration
@Log4j2
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping() {
            @Override
            protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
                RequestMappingInfo mappingForMethod = super.getMappingForMethod(method, handlerType);
                CustomRestController customRestController = method.getDeclaringClass().getAnnotation(CustomRestController.class);
                if (mappingForMethod != null && customRestController != null) {
                    return RequestMappingInfo.paths(String.format("api/v%s", customRestController.apiVersion())).build().combine(mappingForMethod);
                } else {
                    return mappingForMethod;
                }
            }
        };
    }
}
