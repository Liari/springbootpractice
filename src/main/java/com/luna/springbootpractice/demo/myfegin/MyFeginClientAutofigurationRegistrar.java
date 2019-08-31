package com.luna.springbootpractice.demo.myfegin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;


/**
 * 类描述: TODO<br>
 *
 * @author ctp
 * @date 2019/8/30 15:27
 */
public class MyFeginClientAutofigurationRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(MyFeginClientAutofigurationRegistrar.class);

    private Environment env;
    private ResourceLoader resourceLoader;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
        if (logger.isDebugEnabled()) {
            logger.debug("setEnvironment");
        }
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        if (logger.isDebugEnabled()) {
            logger.debug("setResourceLoader");
        }
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (logger.isDebugEnabled()) {
            logger.debug("registerBeanDefinitions");
        }

        // 获取MyFeginClientScanner配置的basePackage
        AnnotationAttributes annoAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(EnableMyFeginClientAutoConfiguration.class.getName()));
        String basePackage = annoAttrs.getString("basePackage");

        //使用implClass来切换代理实现方式
        //@EnableMyFeginClientAutoConfiguration(basePackage = "com.luna.springbootpractice.demo.service",implClass = MyFeginClientCgLibFactoryBean.class)
        Class<? extends AbstractFactoryBean> implClass = annoAttrs.getClass("implClass");

        // 扫描带有MyFeginClientScanner注解的接口
        MyFeginClientClasspathScanner scanner = new MyFeginClientClasspathScanner(registry);
        scanner.setResourceLoader(resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(MyFeginClientScanner.class));

        scanner.setFactoryBeanImplClass(implClass);
        scanner.doScan(basePackage);
    }

}
