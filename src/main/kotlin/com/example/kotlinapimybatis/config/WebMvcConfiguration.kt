package com.example.kotlinapimybatis.config

import com.example.kotlinapimybatis.web.interceptor.TodoInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration : WebMvcConfigurer {

    override fun addInterceptors(interceptorRegistry: InterceptorRegistry) {

        interceptorRegistry.addInterceptor(TodoInterceptor())
            .addPathPatterns("/api/*")
    }
}