package com.recap.recapsystemmanager.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.DelegatingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

@Configuration
@EnableWebSecurity
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(AuthSecurityConfig.class);
	
	@Value("${iri.corp.ldap.username}")
	private String ldapUsername;
	
	@Value("${iri.corp.ldap.password}")
	private String ldapUserPassword;
	
	@Value("${iri.corp.ldap.url}")
	private String ldapUrl;
	
	@Value("${iri.corp.ldap.base}")
	private String ldapBase;
	
	@Value("${iri.corp.ldap.userFilter}")
	private String userFilter;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		// authentication manager (see below)
		auth.ldapAuthentication()
		.contextSource()
        .url(ldapUrl + ldapBase)
        .managerDn(ldapUsername)
        .managerPassword(ldapUserPassword)
        .and()
        .userSearchFilter(userFilter);
	}	

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		// http builder configurations for authorize requests and form login (see below)
		http.addFilterBefore(
				new LoginPageFilter(), DefaultLoginPageGeneratingFilter.class);
		
		http
		.authorizeRequests()  
        .anyRequest().authenticated()  
        .and()  
        .formLogin()  
        .defaultSuccessUrl("/getPauseProcess").permitAll()
        .and()
        .logout()
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID");

	}

	private LogoutSuccessHandler logoutSuccessHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	private AuthenticationFailureHandler authenticationFailureHandler() {
		// TODO Auto-generated method stub
		return new SimpleUrlAuthenticationFailureHandler();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}
