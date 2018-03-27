package com.framework.backend.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomRestController {
    @AliasFor("apiVersion")
    String value() default "1";

    @AliasFor("value")
    String apiVersion() default "1";
}
