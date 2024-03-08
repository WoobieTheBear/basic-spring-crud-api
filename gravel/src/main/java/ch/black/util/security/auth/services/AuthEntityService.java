package ch.black.util.security.auth.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import ch.black.util.security.auth.dtos.SignupFormDTO;
import ch.black.util.security.auth.entities.AuthEntity;

public interface AuthEntityService extends UserDetailsService {
    
    public AuthEntity findByName(String name);

    void save(SignupFormDTO insertedEntity);

}
