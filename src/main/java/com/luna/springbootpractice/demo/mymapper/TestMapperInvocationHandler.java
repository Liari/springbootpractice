package com.luna.springbootpractice.demo.mymapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 类描述: TODO<br>
 *
 * @author ctp
 * @date 2019/8/30 17:51
 */
public class TestMapperInvocationHandler implements InvocationHandler {

    public TestMapperInvocationHandler() {
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args != null && args.length > 0) {
            Object object = args[0];
            return object;
        }
        return null;
    }

}
