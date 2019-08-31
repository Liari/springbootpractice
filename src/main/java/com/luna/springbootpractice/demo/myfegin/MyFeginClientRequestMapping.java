package com.luna.springbootpractice.demo.myfegin;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MyFeginClientRequestMapping {
    String value() default "";
}