package com.softserverinc.edu.configs;

import com.softserverinc.edu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Security configuration with Spring Security library
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    /**
     * Global instance for holding authentication in memory.
     * By extending WebSecurityConfigurerAdapter we can use
     * Autowired annotation for authentication manager.
     * @param auth Manager authentication
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {


        auth.jdbcAuthentication().
                dataSource(dataSource)
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
}
