package com.wlwq.annotation;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Rick wlwq
 * @Description 验证手机号，空和正确的手机号都能验证通过<br/> 正确的手机号由11位数字组成，第一位为1 第二位为 3、4、5、7、8.9
 * @Date 2021/2/6 15:15
 */
@ConstraintComposition(CompositionType.OR)
@Pattern(regexp = "1[3|4|5|7|8|9][0-9]\\d{8}")
@Length(min = 11, max = 11)
@Documented
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface Phone {
    String message() default "手机号校验错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
