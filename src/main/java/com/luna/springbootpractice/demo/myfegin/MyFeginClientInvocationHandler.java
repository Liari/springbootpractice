package com.luna.springbootpractice.demo.myfegin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 类描述: TODO<br>
 *
 * @author ctp
 * @date 2019/8/30 20:09
 */
public class MyFeginClientInvocationHandler implements InvocationHandler {

    private MyFeginClientSupport myFeginClientSupport;

    public MyFeginClientInvocationHandler(MyFeginClientSupport myFeginClientSupport) {
        this.myFeginClientSupport = myFeginClientSupport;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return myFeginClientSupport.invoke(proxy,method, args);
    }
}
