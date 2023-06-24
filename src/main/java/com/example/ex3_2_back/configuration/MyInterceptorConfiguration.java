package com.example.ex3_2_back.configuration;

import com.example.ex3_2_back.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptorConfiguration implements WebMvcConfigurer {

    AuthInterceptor authInterceptor;

    @Autowired
    public void setAuthInterceptor(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry
                .addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                //放行路径，不用验证token
                .excludePathPatterns(
                        "/auth/**",
                        "/favicon.ico",
                        "/doc.html",
                        "/doc.html#/**",
                        "/css/**",
                        "/webjars/**",
                        "/ex3_2_back/swagger-ui.html"
                );

    }
}
