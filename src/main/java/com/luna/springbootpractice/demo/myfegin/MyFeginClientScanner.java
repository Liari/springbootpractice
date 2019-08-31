package com.luna.springbootpractice.demo.myfegin;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyFeginClientScanner {
    String url() default "";
}
