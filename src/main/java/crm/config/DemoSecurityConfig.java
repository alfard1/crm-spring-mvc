package crm.config;

import crm.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DemoSecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/customer/showForm*").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/customer/save*").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/customer/delete").hasRole("ADMIN")
                .antMatchers("/customer/**").hasRole("EMPLOYEE")

                .antMatchers("/product/showForm*").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/product/save*").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/product/delete").hasRole("ADMIN")
                .antMatchers("/product/**").hasRole("EMPLOYEE")

                .antMatchers("/comments/showForm*").hasRole("EMPLOYEE")
                .antMatchers("/comments/save*").hasRole("EMPLOYEE")
                .antMatchers("/comments/delete").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers("/comments/**").hasRole("EMPLOYEE")

                .antMatchers("/home").hasRole("EMPLOYEE")
                .antMatchers("/api").hasRole("EMPLOYEE")
                .antMatchers("/more-info").hasRole("EMPLOYEE")
                .antMatchers("/register*").permitAll()
                .antMatchers("/resources/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder); //set the password encoder - bcrypt
        return auth;
    }
}
