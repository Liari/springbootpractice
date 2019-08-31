package com.luna.springbootpractice.demo.myfegin;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(MyFeginClientAutofigurationRegistrar.class)
public @interface EnableMyFeginClientAutoConfiguration {
    String basePackage() default "";
    //设置代理类实现，默认为使用JDK动态代理
    Class<? extends AbstractMyFeginClientFactoryBean> implClass() default MyFeginClientFactoryBean.class;
}