package ch.black.util.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BlackSecurityConfig {

    public enum Role {
        USER("USER"),
        POWER("POWER"),
        ADMIN("ADMIN");
        private String value;

        Role(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        // connecting Jdbc to PostgreSQL-database
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        // this will get the user entries from the table "auth_entities"
        manager.setUsersByUsernameQuery("SELECT entity_name, entity_key, entity_active FROM auth_entity WHERE entity_name = ?");
        // this will get the permission entries from the table "auth_permissions"
        // manager.setAuthoritiesByUsernameQuery("SELECT entity_name, permission_name FROM auth_permission WHERE entity_name = ?");
        manager.setAuthoritiesByUsernameQuery("SELECT entity_name, permission_name FROM auth_access_tuple as tuple INNER JOIN auth_entity as entity ON tuple.entity_id = entity.id INNER JOIN auth_permission as perm ON tuple.permission_id = perm.id WHERE entity_name = ?");

        // following lines will check the database connection and the default user schema
        UserDetails userDetails = manager.loadUserByUsername("john");
        System.out.println("BlackSecurityConfig.userDetailsManager() user name: " + userDetails.getUsername());
        System.out.println("BlackSecurityConfig.userDetailsManager() user pass: " + userDetails.getPassword());
        System.out.println("BlackSecurityConfig.userDetailsManager() user role: " + userDetails.getAuthorities().iterator().next());

        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSec) throws Exception {

        // these paths are relative to "server.servlet.context-path" so you do not have to add "/black"
        // example for RestController (no-context-path)/request-mapping-path/endpoint
        // example for automagic rest (no-context-path)/<rest.base-path>/(<class-name> or RepositoryRestResource.path)
        // If you are using an InMemoryUserDetailsManager ".hasAuthority" is called ".hasRole"
        // because there are not enough ominous functions hidden in the spring framework yet
        httpSec.authorizeHttpRequests(configurer -> {
            configurer
            .requestMatchers(
                "/", "/accessDenied", "/css/**", "/js/**", "/img/**"
            ).permitAll()
            // [START] web paths
            .requestMatchers(HttpMethod.GET, "/user/profile").authenticated()
            .requestMatchers(HttpMethod.GET, "/people/list").hasAuthority(""+Role.USER)
            .requestMatchers(HttpMethod.GET, "/people/form/**").hasAuthority(""+Role.POWER)
            .requestMatchers(HttpMethod.POST, "/people/save").hasAuthority(""+Role.POWER)
            .requestMatchers(HttpMethod.GET, "/people/delete").hasAuthority(""+Role.ADMIN)
            .requestMatchers(HttpMethod.GET, "/companies/list").hasAuthority(""+Role.POWER)
            .requestMatchers(HttpMethod.GET, "/companies/form/**").hasAuthority(""+Role.ADMIN)
            .requestMatchers(HttpMethod.POST, "/companies/save").hasAuthority(""+Role.ADMIN)
            .requestMatchers(HttpMethod.GET, "/companies/delete").hasAuthority(""+Role.ADMIN)
            .requestMatchers(HttpMethod.GET, "/workcontracts/list").hasAuthority(""+Role.ADMIN)
            .requestMatchers(HttpMethod.GET, "/workcontracts/form/**").hasAuthority(""+Role.ADMIN)
            .requestMatchers(HttpMethod.POST, "/workcontracts/save").hasAuthority(""+Role.ADMIN)
            .requestMatchers(HttpMethod.GET, "/workcontracts/delete").hasAuthority(""+Role.ADMIN)
            // [START] api paths
            // this path is completely custom from BlackRestController
            .requestMatchers(HttpMethod.GET, "/api/test").hasAuthority(""+Role.USER)
            // following paths are handled by the PersonDAO in the background
            .requestMatchers(HttpMethod.GET, "/api/people").hasAuthority(""+Role.USER)
            .requestMatchers(HttpMethod.GET, "/api/people/**").hasAuthority(""+Role.USER)
            .requestMatchers(HttpMethod.POST, "/api/people").hasAuthority(""+Role.POWER)
            .requestMatchers(HttpMethod.PUT, "/api/people").hasAuthority(""+Role.POWER)
            .requestMatchers(HttpMethod.DELETE, "/api/people/**").hasAuthority(""+Role.ADMIN)
            // following paths are handled by the CompanyRepository in the background
            .requestMatchers(HttpMethod.GET, "/api/companies").hasAuthority(""+Role.USER)
            .requestMatchers(HttpMethod.GET, "/api/companies/**").hasAuthority(""+Role.USER)
            .requestMatchers(HttpMethod.POST, "/api/companies").hasAuthority(""+Role.POWER)
            .requestMatchers(HttpMethod.PUT, "/api/companies").hasAuthority(""+Role.POWER)
            .requestMatchers(HttpMethod.DELETE, "/api/companies/**").hasAuthority(""+Role.ADMIN)
            // following paths are automagically created by "spring-boot-starter-data-rest" and annotations
            .requestMatchers(HttpMethod.GET, "/api/workcontracts").hasAuthority(""+Role.USER)
            .requestMatchers(HttpMethod.GET, "/api/workcontracts/**").hasAuthority(""+Role.USER)
            .requestMatchers(HttpMethod.POST, "/api/workcontracts").hasAuthority(""+Role.POWER)
            .requestMatchers(HttpMethod.PUT, "/api/workcontracts/**").hasAuthority(""+Role.POWER)
            .requestMatchers(HttpMethod.DELETE, "/api/workcontracts/**").hasAuthority(""+Role.ADMIN);
        }).exceptionHandling(exceptionHandler -> {
            exceptionHandler.accessDeniedPage("/accessDenied");
        })
        .formLogin(form -> {
            form.loginPage("/login")
            .loginProcessingUrl("/processAuth")
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

    /* 
    // [NOTE]: this is for in memory user management, please do not use this in production
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails john = User.builder()
        .username("john")
        .password("{noop}changeme")
        .roles(Role.USER.toString())
        .build();
        
        return new InMemoryUserDetailsManager(john);
    }
    */
}
