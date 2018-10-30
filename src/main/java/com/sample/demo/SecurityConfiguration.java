package com.sample.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**************************************************************************************************************************************************/
	// This block of commented code demos working of login page which on success sends request to /welcome request to controller
	
	@Autowired
	CustomAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		//We are allowing anonymous access on /login so that users can authenticate. We are also securing everything else.
		//roles the roles to require (i.e. USER, ADMIN, etc). Note, it should not start with "ROLE_" as this is automatically inserted.
		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/welcome").hasAnyRole("USER", "ADMIN")
		.antMatchers("/getEmployees").hasAnyRole("USER", "ADMIN").antMatchers("/addNewEmployee")
		.hasAnyRole("ADMIN").anyRequest().authenticated()
		.and().formLogin().loginPage("/login")
		//.defaultSuccessUrl("/welcome") //default success redirection to /welcome page
		.successHandler(customizeAuthenticationSuccessHandler)
		.failureUrl("/error1")
		.permitAll()
		.and().logout().permitAll();

		http.csrf().disable();
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		authenticationMgr.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).authorities("ROLE_ADMIN").and()
				.withUser("sample").password(passwordEncoder().encode("sample")).authorities("ROLE_USER", "ROLE_ADMIN").and()
				.withUser("user").password(passwordEncoder().encode("user")).authorities("ROLE_USER");
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
	/**************************************************************************************************************************************************/
	
	//WORKING METHOD
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/welcome").permitAll() //public endpoint will be /welcome, for this there will be no authentication localhost:8888/welcome
		.anyRequest().authenticated() //other requests will get the default login page for security
		.and().formLogin()
		.permitAll().and().logout().permitAll();
	}*/
	//default username will be user (springboot framework)
	//password will be printed in the server logs like below. 
	//Using generated security password: 281c1ad6-ae2d-41f7-8284-1eb8eaaff302
	//the above username and password can be used to login
		
	/*
	 * The name of the configureGlobal method is not important. 
	 * However, it is important to only configure AuthenticationManagerBuilder in a class annotated with either 
	 * @EnableWebSecurity, @EnableGlobalMethodSecurity, or @EnableGlobalAuthentication. 
	 * Doing otherwise has unpredictable results.
	 * 
	 * */
	
	/*
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER");
	}*/
	
}
