package ch.black.util.security.auth.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ch.black.util.security.auth.entities.AuthPermission;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class AuthPermissionDAOImpl implements AuthPermissionDAO {

    private EntityManager manager;

    @Autowired
    public AuthPermissionDAOImpl(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public AuthPermission findPermissionByName(String searchName) {
        
        TypedQuery<AuthPermission> query = manager.createQuery("FROM AuthPermission WHERE permissionName = :whereName", AuthPermission.class);
        query.setParameter("whereName", searchName);

        AuthPermission permission = null;
        try {
            permission = query.getSingleResult();
        } catch (Exception e) {
            permission = null;
        }

        return permission;
    }
    
}
