package com.domdd.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials( true );
        corsConfiguration.addExposedHeader("Content-Type");
        corsConfiguration.addExposedHeader("X-Requested-With");
        corsConfiguration.addExposedHeader("accept");
        corsConfiguration.addExposedHeader("Origin");
        corsConfiguration.addExposedHeader("Access-Control-Request-Method");
        corsConfiguration.addExposedHeader("Access-Control-Request-Headers");
        corsConfiguration.addExposedHeader("partyId");
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 对接口配置跨域设置
        return new CorsFilter(source);
    }
}
