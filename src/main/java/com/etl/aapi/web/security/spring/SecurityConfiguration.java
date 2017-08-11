package com.etl.aapi.web.security.spring;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.etl.aapi.web.security.JwtTokenFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	public static String HEADER_PREFIX = "Bearer";
	public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
			.disable()
			//.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
			//.authorizeRequests()
	        //.antMatchers("/login")
	        //.permitAll()
	        //.antMatchers("/**/*")
	        //.denyAll()
			;
    }
	@Bean
    public FilterRegistrationBean greetingFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("jwtTokenFilter");
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter();
        registrationBean.setFilter(jwtTokenFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }
}