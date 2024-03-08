package ch.black.util.security.auth.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ch.black.util.security.auth.daos.AuthEntityDAO;
import ch.black.util.security.auth.daos.AuthPermissionDAO;
import ch.black.util.security.auth.dtos.SignupFormDTO;
import ch.black.util.security.auth.entities.AuthEntity;
import ch.black.util.security.auth.entities.AuthPermission;

@Service
public class AuthEntityServiceImpl implements AuthEntityService {

    private AuthEntityDAO entityDAO;

    private AuthPermissionDAO permissionDAO;

    private BCryptPasswordEncoder encoder;

    @Autowired
    public AuthEntityServiceImpl(AuthEntityDAO entityDAO, AuthPermissionDAO permissionDAO, BCryptPasswordEncoder encoder) {
        this.entityDAO = entityDAO;
        this.permissionDAO = permissionDAO;
        this.encoder = encoder;
    }

    @Override
    public AuthEntity findByName(String name) {
        return entityDAO.findEntityByName(name);
    }

    @Override
    public void save(SignupFormDTO insertedEntity) {
        AuthEntity entity = new AuthEntity();

        entity.setEntityName(insertedEntity.getName());
        entity.setEmail(insertedEntity.getEmail());
        entity.setPassword(encoder.encode(insertedEntity.getPassword()));
        entity.setGroup(Long.valueOf(1));
        entity.setActive(true);
        
        // this will give every new entity the permissions of a user at first
        entity.setPermissions(Arrays.asList(permissionDAO.findPermissionByName("USER")));

        entityDAO.save(entity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthEntity entity = entityDAO.findEntityByName(username);

        if(entity == null){
            throw new UsernameNotFoundException("The combination of username and password can not be found");
        }

        Collection<SimpleGrantedAuthority> authorities = mapPermissions(entity.getPermissions());

        return new User(entity.getEntityName(), entity.getPassword(), authorities);
    }

    private Collection<SimpleGrantedAuthority> mapPermissions(Collection<AuthPermission> permissions) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(AuthPermission permission : permissions){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission.getPermissionName());
            authorities.add(authority);
        }

        return authorities;
    }
    
}
