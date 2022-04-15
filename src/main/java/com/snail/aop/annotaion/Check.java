package com.snail.aop.annotaion;
import com.snail.aop.paramcheck.CheckType;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 常用校验  枚举
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Check.CheckValidator.class)// 注意这里，指出了自定义的校验方法
public @interface Check {

    String message() default ""; // 自定义异常返回信息

    CheckType type(); // 自定义校验字段

    Class<?>[] groups() default {};

    Class<? extends javax.validation.Payload>[] payload() default {};

    /**
     * 校验实现
     * 实现ConstraintValidator接口，这是个泛型接口，泛型中第一个是自定义的注解，第二个是注解使用的类型。
     * 这里就是我们调用的字段校验方法
     */
    class CheckValidator implements ConstraintValidator<Check,Object>{
        private CheckType type;

        @Override
        public void initialize(Check constraintAnnotation) {
            this.type=constraintAnnotation.type();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            return CheckType.validate(type,value);
        }
    }
}