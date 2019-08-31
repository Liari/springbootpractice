package com.luna.springbootpractice.demo.myfegin;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 类描述: TODO<br>
 *
 * @author ctp
 * @date 2019/8/30 15:31
 */
public class MyFeginClientCgLibFactoryBean<T> extends AbstractMyFeginClientFactoryBean<T> implements MethodInterceptor {

    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(this);
        enhancer.setSuperclass(getMapperInterface());
        setProxyTarget((T) enhancer.create());
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        return getMyFeginClientSupport().invoke(obj,method, args);
    }

}