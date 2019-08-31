package com.luna.springbootpractice.demo.myfegin;

import java.lang.reflect.Method;

public interface MyFeginClientSupport {
    Object invoke(Object proxy, Method method, Object[] args);
}
