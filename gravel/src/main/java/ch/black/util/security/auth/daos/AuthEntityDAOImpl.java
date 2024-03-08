package ch.black.util.security.auth.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ch.black.util.security.auth.entities.AuthEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class AuthEntityDAOImpl implements AuthEntityDAO {

    private EntityManager manager;

    @Autowired
    public AuthEntityDAOImpl(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public AuthEntity findEntityByName(String searchName) {
        TypedQuery<AuthEntity> query = manager.createQuery("FROM BlackAuthEntity WHERE entityName = :whereName AND active = true", AuthEntity.class);
        query.setParameter("whereName", searchName);

        AuthEntity entity = null;
        try {
            entity = query.getSingleResult();
        } catch (Exception e) {
            entity = null;
        }

        return entity;
    }

    @Override
    @Transactional
    public void save(AuthEntity entity) {
        manager.merge(entity);
    }
    
}
