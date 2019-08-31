package com.luna.springbootpractice.demo.mymapper;

import com.luna.springbootpractice.demo.service.TestMapper;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 类描述: TODO<br>
 *
 * @author ctp
 * @date 2019/8/30 17:49
 */
@Component
public class TestMapperProxyFactoryBean implements FactoryBean<TestMapper> {

    @Override
    public TestMapper getObject() throws Exception {
        Class<?> interfaceType = TestMapper.class;
        InvocationHandler handler = new TestMapperInvocationHandler();
        TestMapper testMapper = (TestMapper) Proxy.newProxyInstance(interfaceType.getClassLoader(),
                new Class[]{interfaceType}, handler);
        return testMapper;
    }

    @Override
    public Class<?> getObjectType() {
        return TestMapper.class;
    }
}
