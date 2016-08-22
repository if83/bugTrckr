package com.softserverinc.edu.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security configuration with Spring Security library
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Global instance for holding authentication in memory. Later it should be done with database.
     * By extending WebSecurityConfigurerAdapter we can use
     * Autowired annotation for authentication manager.
     * @param auth Manager authentication
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("admin@ss.com")
                .password("admin")
                    .roles("ADMIN");

        auth.inMemoryAuthentication()
                .withUser("manager@ss.com")
                .password("manager")
                    .roles("PROJECT_MANAGER");

        auth.inMemoryAuthentication()
                .withUser("developer@ss.com")
                .password("developer")
                .roles("DEVELOPER");

        auth.inMemoryAuthentication()
                .withUser("quality_e1@ss.com")
                .password("quality_e1")
                .roles("QA");

        auth.inMemoryAuthentication()
                .withUser("user1@ss.com")
                .password("user1")
                .roles("USER");

        auth.inMemoryAuthentication()
                .withUser("guest1@ss.com")
                .password("guest1")
                .roles("GUEST");
    }


    /**
     * Restricting URL usage. Here put custom logic.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
            //disable token in views for simplisity, in other wat we need to supply all security token for all jsps
            http.csrf().disable()
                    .authorizeRequests()
                        .antMatchers("/admin**").hasRole("ADMIN")
                        .antMatchers("/**").hasAnyRole("ADMIN", "PROJECT_MANAGER", "DEVELOPER", "USER", "GUEST")
                        .anyRequest().authenticated()
                    .and()
                        .formLogin()
                            .loginPage("/")
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
