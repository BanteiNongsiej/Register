package com.example.Register.config;

import com.example.Register.repository.UserRepository;
import com.example.Register.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserRepository userLoginRepo;

    @Autowired
    public SecurityConfig(UserRepository userLoginRepo) {
        this.userLoginRepo = userLoginRepo;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/resources/**", "/static/**", "/assets/**", "/css/**", "img/**", "js/**", "jquery-3.6.0/**").permitAll()
//                        .requestMatchers("/login", "test", "/register").permitAll() //,"/","/about"
                        //						.requestMatchers().permitAll()
                        .anyRequest()
                        //.permitAll()
                        .authenticated()
                )
                .formLogin((login) -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/createStudent")
                        .failureUrl("/login?error=Authentication Error")
                        .permitAll())
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .sessionManagement(
                        (session) -> session
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .sessionFixation()
                                .migrateSession()
                                .invalidSessionUrl("/")
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(true))
                .exceptionHandling((exception) -> exception
                        .accessDeniedHandler(((request, response, accessDeniedException) -> {
                            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        })));

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new LoginService(userLoginRepo);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    /*
     * @Bean public AuthenticationSuccessHandler authenticationSuccessHandler(){
     * return new LoginSuccessHandler(); }
     */
}
