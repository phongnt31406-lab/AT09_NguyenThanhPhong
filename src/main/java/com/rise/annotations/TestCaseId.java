package com.rise.annotations;

import io.qameta.allure.LabelAnnotation;
import io.qameta.allure.LinkAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mã manual test case (VD RISE_LOGIN_TC_001) — ghi vào Allure dưới label "testId" (lọc/truy vết)
 * và link TMS (hiển thị ở mục Links, URL theo allure.link.tms.pattern).
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@LabelAnnotation(name = "testId")
@LinkAnnotation(type = "tms")
public @interface TestCaseId {
    String value();
}
