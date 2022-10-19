package com.example.ecommerce_web.validator;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanProvider.applicationContext = applicationContext;
    }

    public static Object getBean(Class cls){
        return BeanProvider.applicationContext.getBean(cls);
    }
}
