package com.ams.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] PUBLIC_URLS = {
												"/swagger-resources/**", "/v2/api-docs","/configuration/ui", 
												"/swagger-resources", "/configuration/security","/swagger-ui.html", 
												"/webjars/**","/swagger-resources/configuration/ui",
												"/external/**",
												"/external/merchant/transaction/add",
												};
	
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final UserDetailsService jwtUserDetailsService;
	private final JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
	    
		httpSecurity.cors().and()
					.csrf().disable()
		   		.authorizeRequests()
				.antMatchers(PUBLIC_URLS).permitAll()
				//.antMatchers("/**").permitAll()
				.anyRequest().authenticated().and()
				.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		httpSecurity.headers().contentSecurityPolicy("script-src 'self' 'unsafe-inline'");
		//httpSecurity.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials","true"));

	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    //@formatter:off
	    super.configure(web);
	    web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
	
	}
	
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
	    return new DefaultHttpFirewall();
	}
}
