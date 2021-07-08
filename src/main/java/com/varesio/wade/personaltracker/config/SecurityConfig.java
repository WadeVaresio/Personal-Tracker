package com.varesio.wade.personaltracker.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity.authorizeRequests()
//                .mvcMatchers("/home").permitAll()
//                .mvcMatchers("/weather/**").permitAll()
//                .mvcMatchers("/swagger-ui/**").permitAll()
//                .mvcMatchers("/api/docs").permitAll()
//                .mvcMatchers("/api/**")
//                .authenticated()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login().permitAll()
//                .and()
//                .logout().logoutSuccessUrl("/home");

        httpSecurity.authorizeRequests()
                .mvcMatchers("/**").permitAll()
                .and()
                .oauth2Login().permitAll()
                .and()
                .logout().logoutSuccessUrl("/")
                .and().csrf().disable();
    }
}
