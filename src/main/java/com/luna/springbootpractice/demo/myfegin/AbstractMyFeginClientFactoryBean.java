package com.luna.springbootpractice.demo.myfegin;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 类描述: TODO<br>
 *
 * @author ctp
 * @date 2019/8/30 15:35
 */
public class AbstractMyFeginClientFactoryBean<T> implements FactoryBean<T>, InitializingBean {

    @Autowired
    private MyFeginClientSupport myFeginClientSupport;

    private Class<?> mapperInterface;

    private T t;

    public void setProxyTarget(T t) {
        setT(t);
    }

    @Override
    public T getObject() throws Exception {
        return  t;
    }

    @Override
    public Class getObjectType() {
        return mapperInterface;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public MyFeginClientSupport getMyFeginClientSupport() {
        return myFeginClientSupport;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public Class<?> getMapperInterface() {
        return mapperInterface;
    }

    public void setMapperInterface(Class<?> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }
}
