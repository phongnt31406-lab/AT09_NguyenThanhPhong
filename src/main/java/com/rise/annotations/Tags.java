package com.rise.annotations;

import io.qameta.allure.LabelAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tags của test trong Allure (VD smoke, regression, login) — mỗi phần tử là 1 label "tag".
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@LabelAnnotation(name = "tag")
public @interface Tags {
    String[] value();
}
