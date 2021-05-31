package com.assocaition.management.commons;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Duanjianhui
 * @date 2021-05-10 10:36 下午
 * @describe
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    // 将登录拦截器配置到容器中
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/foreground/toMyAssociation","/foreground/toPersonal");
    }
}
