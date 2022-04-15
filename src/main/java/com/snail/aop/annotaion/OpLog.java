package com.snail.aop.annotaion;

import com.snail.aop.operation.OpType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OpLog {

    public OpType opType();

    public String opItem();

    public String OpItemIdExpression();
}
