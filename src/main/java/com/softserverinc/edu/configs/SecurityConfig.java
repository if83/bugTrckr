package com.softserverinc.edu.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * Security configuration with Spring Security library
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    /**
     * By extending WebSecurityConfigurerAdapter we can use
     * Autowired annotation for authentication manager.
     *
     * @param auth Manager authentication
     */
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {


        auth.jdbcAuthentication().
                dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "select email,password, enabled from User where email=?")
                .authoritiesByUsernameQuery(
                        "select email, role from User where email=?");


    }


    /**
     * Restricting URL usage. Here put custom logic.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        //disable token in views for simplisity, in other wat we need to supply all security token for all jsps
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin**").hasRole("ADMIN")
                .antMatchers("/users").hasAnyRole("ADMIN", "PROJECT_MANAGER")
                //TODO:Lyutak configure more detailed access
                .antMatchers("/**").hasAnyRole("ADMIN", "PROJECT_MANAGER", "DEVELOPER", "USER", "GUEST")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/")
                .successForwardUrl("/")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");
    }


    /**
     * Configure to ignore url resources
     *
     * @param web - security object
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/fonts/**")
                .antMatchers("/images/**")
                .antMatchers("/about");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        return encoder;
    }
}
