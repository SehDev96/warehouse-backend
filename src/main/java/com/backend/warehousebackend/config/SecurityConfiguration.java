package com.backend.warehousebackend.config;

import com.backend.warehousebackend.constant.Role;
import com.backend.warehousebackend.filter.AppAuthenticationFilter;
import com.backend.warehousebackend.filter.AppAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder bCryptPasswordEncoder,
                                                       UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and().build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOriginPatterns(
                        "http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
                WebMvcConfigurer.super.addCorsMappings(registry);
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(http, passwordEncoder(), userDetailsService);

        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests((authz) -> authz
                .requestMatchers(HttpMethod.GET, "/app/warehouse/codelist").hasAnyAuthority(Role.ADMIN.name(), Role.MANAGER.name(), Role.OPERATOR.name())
                .requestMatchers("/app/product/**").hasAnyAuthority(Role.ADMIN.name(), Role.MANAGER.name())
                .requestMatchers("/app/warehouse/**").hasAnyAuthority(Role.ADMIN.name(), Role.MANAGER.name())
                .requestMatchers("/app/transaction/**").hasAnyAuthority(Role.ADMIN.name(), Role.MANAGER.name(), Role.OPERATOR.name())
                .requestMatchers("/app/auth/**").permitAll()
                .anyRequest().authenticated()
        );

        http.authenticationManager(authenticationManager);
        http.addFilterBefore(new AppAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(new AppAuthenticationFilter(authenticationManager));
        return http.build();
    }
}
