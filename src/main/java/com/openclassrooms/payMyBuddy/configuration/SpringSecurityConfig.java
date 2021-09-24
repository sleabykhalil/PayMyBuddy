package com.openclassrooms.payMyBuddy.configuration;

import com.openclassrooms.payMyBuddy.services.servicesImpl.ClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(clientDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // .antMatchers("/*").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                //.antMatchers("/transfer**").access("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
                //.antMatchers("/transfer").access("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
                .antMatchers("/transfer").hasAuthority("CLIENT")
                //.antMatchers("/transfer/**").access("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated().and().httpBasic()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/transfer");
/*                .usernameParameter("client_email")
                .passwordParameter("password");*/
        //.and()
        //.oauth2Login();
    }

/*    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }*/

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
