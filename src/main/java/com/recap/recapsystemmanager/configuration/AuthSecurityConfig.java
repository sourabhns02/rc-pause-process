package com.recap.recapsystemmanager.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		// authentication manager (see below)
		auth.ldapAuthentication().userDnPatterns("CN=ldapadmin,OU=Utility Accounts,DC=infores,DC=com")
		.userSearchBase("(&(objectCategory=user)(sAMAccountName={0}))")
		.groupSearchSubtree(true)
        .contextSource()
        .url("ldap://crpdcw201p.infores.com:3268/DC=infores,DC=com")
        .managerDn("ldapadmin")
        .managerPassword("LD@P:adm1n");
        
        logger.info("test "+auth.toString());
	}
	
//	@Override
//	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//		// authentication manager (see below)
//		auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
//				.and().withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER").and()
//				.withUser("admin").password(passwordEncoder().encode("123")).roles("ADMIN");
//	}
	
//	@Override
//	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//		// authentication manager (see below)
//		auth.jdbcAuthentication()
//	}

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
        .loginPage("/login")
        .defaultSuccessUrl("/getPauseProcess")
        .failureUrl("/login-error.html").permitAll();

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
