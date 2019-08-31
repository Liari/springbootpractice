package com.luna.springbootpractice.demo.myfegin;

import java.lang.reflect.Proxy;

/**
 * 类描述: TODO<br>
 *
 * @author ctp
 * @date 2019/8/30 15:31
 */
public class MyFeginClientFactoryBean<T> extends AbstractMyFeginClientFactoryBean<T> {

    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() throws Exception {
        T target = (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { getMapperInterface() },
                new MyFeginClientInvocationHandler(getMyFeginClientSupport()) );
        setProxyTarget(target);
    }

}