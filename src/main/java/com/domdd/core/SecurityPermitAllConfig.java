//package com.domdd.core;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * @author lw
// * @date 2022/4/12 3:22 下午
// */
////@Configuration
//public class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll()
//                .and().csrf().disable();
//    }
//
//}