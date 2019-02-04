/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mitrais.rms.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;

/**
 *
 * @author Ustadho_1218
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, concat('{bcrypt}', p.password) as password , active as enabled\n"
                        + "from c_user u \n"
                        + "inner join c_user_password p on p.user_id=u.id \n"
                        + "where username=?")
                .authoritiesByUsernameQuery("select u.username, a.name as authority\n"
                        + "from c_user u \n"
                        + "inner join c_role r on r.id=u.role_id\n"
                        + "inner join c_role_authority rp on rp.role_id=r.id\n"
                        + "inner join c_authority a on a.id=rp.authority_id\n"
                        + "where u.username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/public.html").permitAll()
                .antMatchers("/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }
    
    public static class AuthConfig extends AuthorizationServerConfigurerAdapter {
    	
    	@Qualifier("authenticationManagerBean")
    	private final AuthenticationManager authenticationManager;
    	
    	public AuthConfig(AuthenticationManager auth ) {
			this.authenticationManager = auth;
		}
    	
    	@Override
    	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    		clients.inMemory()
    		.withClient("angularApp")
    		.authorizedGrantTypes("authorization_code", "refresh_token")
    		.scopes("list-user", "user-info")
    		.authorities("OAUTH_CLIENT")
    		.accessTokenValiditySeconds(180);
    	}
    }

}
