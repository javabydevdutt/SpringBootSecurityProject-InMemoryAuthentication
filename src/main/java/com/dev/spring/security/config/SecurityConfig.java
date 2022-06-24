package com.dev.spring.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("sam").password("{noop}sam").authorities("ADMIN");
		auth.inMemoryAuthentication().withUser("syed").password("{noop}syed").authorities("EMPLOYEE");
		auth.inMemoryAuthentication().withUser("jai").password("{noop}jai").authorities("STUDENT");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		// URL-Access Type
		.antMatchers("/home ").permitAll()
		.antMatchers("/welcome").authenticated()
		.antMatchers("/admin").hasAuthority("ADMIN")
		.antMatchers("/emp").hasAuthority("EMPLOYEE")
		.antMatchers("/std").hasAuthority("STUDENT")
		
		// LoginForm Details
		.and()
		.formLogin()
		.defaultSuccessUrl("/welcome", true)
		
		// Logout Details
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		// Exception Details
		.and()
		.exceptionHandling()
		.accessDeniedPage("/denied");
	}
}
