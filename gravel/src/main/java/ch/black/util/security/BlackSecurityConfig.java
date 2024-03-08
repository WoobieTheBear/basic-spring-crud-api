package ch.black.util.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ch.black.util.security.auth.services.AuthEntityService;

@Configuration
public class BlackSecurityConfig {

    public enum PermissionName {
        USER("USER"),
        POWER("POWER"),
        ADMIN("ADMIN");
        private String value;

        PermissionName(final String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }

        @Override
        public String toString() {
            return this.get();
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(AuthEntityService entityService){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(entityService);
        auth.setPasswordEncoder(passwordEncoder());

        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSec, BlackSuccessHandler blackSuccessHandler) throws Exception {

        // these paths are relative to "server.servlet.context-path" so you do not have to add "/black"
        // example for RestController (no-context-path)/request-mapping-path/endpoint
        // example for automagic rest (no-context-path)/<rest.base-path>/(<class-name> or RepositoryRestResource.path)
        // If you are using an InMemoryUserDetailsManager ".hasAuthority" is called ".hasRole"
        // because there are not enough ominous functions hidden in the spring framework yet
        httpSec.authorizeHttpRequests(configurer -> {
            configurer
            .requestMatchers(
                "/", "/signup", "/processSignup", "/accessDenied", "/css/**", "/js/**", "/img/**"
            ).permitAll()
            // [START] web paths
            .requestMatchers(HttpMethod.GET, "/user/profile").authenticated()
            .requestMatchers(HttpMethod.GET, "/people/list").hasAuthority(""+PermissionName.USER)
            .requestMatchers(HttpMethod.GET, "/people/form/**").hasAuthority(""+PermissionName.POWER)
            .requestMatchers(HttpMethod.POST, "/people/save").hasAuthority(""+PermissionName.POWER)
            .requestMatchers(HttpMethod.GET, "/people/delete").hasAuthority(""+PermissionName.ADMIN)
            .requestMatchers(HttpMethod.GET, "/companies/list").hasAuthority(""+PermissionName.POWER)
            .requestMatchers(HttpMethod.GET, "/companies/form/**").hasAuthority(""+PermissionName.ADMIN)
            .requestMatchers(HttpMethod.POST, "/companies/save").hasAuthority(""+PermissionName.ADMIN)
            .requestMatchers(HttpMethod.GET, "/companies/delete").hasAuthority(""+PermissionName.ADMIN)
            .requestMatchers(HttpMethod.GET, "/workcontracts/list").hasAuthority(""+PermissionName.ADMIN)
            .requestMatchers(HttpMethod.GET, "/workcontracts/form/**").hasAuthority(""+PermissionName.ADMIN)
            .requestMatchers(HttpMethod.POST, "/workcontracts/save").hasAuthority(""+PermissionName.ADMIN)
            .requestMatchers(HttpMethod.GET, "/workcontracts/delete").hasAuthority(""+PermissionName.ADMIN)
            // [START] api paths
            // this path is completely custom from BlackRestController
            .requestMatchers(HttpMethod.GET, "/api/test").hasAuthority(""+PermissionName.USER)
            // following paths are handled by the PersonDAO in the background
            .requestMatchers(HttpMethod.GET, "/api/people").hasAuthority(""+PermissionName.USER)
            .requestMatchers(HttpMethod.GET, "/api/people/**").hasAuthority(""+PermissionName.USER)
            .requestMatchers(HttpMethod.POST, "/api/people").hasAuthority(""+PermissionName.POWER)
            .requestMatchers(HttpMethod.PUT, "/api/people").hasAuthority(""+PermissionName.POWER)
            .requestMatchers(HttpMethod.DELETE, "/api/people/**").hasAuthority(""+PermissionName.ADMIN)
            // following paths are handled by the CompanyRepository in the background
            .requestMatchers(HttpMethod.GET, "/api/companies").hasAuthority(""+PermissionName.USER)
            .requestMatchers(HttpMethod.GET, "/api/companies/**").hasAuthority(""+PermissionName.USER)
            .requestMatchers(HttpMethod.POST, "/api/companies").hasAuthority(""+PermissionName.POWER)
            .requestMatchers(HttpMethod.PUT, "/api/companies").hasAuthority(""+PermissionName.POWER)
            .requestMatchers(HttpMethod.DELETE, "/api/companies/**").hasAuthority(""+PermissionName.ADMIN)
            // following paths are automagically created by "spring-boot-starter-data-rest" and annotations
            .requestMatchers(HttpMethod.GET, "/api/workcontracts").hasAuthority(""+PermissionName.USER)
            .requestMatchers(HttpMethod.GET, "/api/workcontracts/**").hasAuthority(""+PermissionName.USER)
            .requestMatchers(HttpMethod.POST, "/api/workcontracts").hasAuthority(""+PermissionName.POWER)
            .requestMatchers(HttpMethod.PUT, "/api/workcontracts/**").hasAuthority(""+PermissionName.POWER)
            .requestMatchers(HttpMethod.DELETE, "/api/workcontracts/**").hasAuthority(""+PermissionName.ADMIN);
        }).exceptionHandling(exceptionHandler -> {
            exceptionHandler.accessDeniedPage("/accessDenied");
        })
        .formLogin(form -> {
            form.loginPage("/login")
            .loginProcessingUrl("/processAuth")
            .successHandler(blackSuccessHandler)
            .permitAll();
        }).logout(LogoutConfigurer::permitAll);

        // set basic auth
        httpSec.httpBasic(Customizer.withDefaults());

        // disable csrf cause there is not frontend forms
        httpSec.csrf(csrf -> csrf.disable());
        // disable cors if you have an angular frontend locally
        // [NOTE]: in production please read up and configure cors properly
        // httpSec.cors(cors -> cors.disable());

        return httpSec.build();
    }

}
