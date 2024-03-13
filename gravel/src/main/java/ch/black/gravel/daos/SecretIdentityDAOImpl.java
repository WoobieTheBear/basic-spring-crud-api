package ch.black.gravel.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ch.black.gravel.entities.SecretIdentity;

@Repository
public class SecretIdentityDAOImpl implements SecretIdentityDAO {
    private EntityManager entityManager;

    @Autowired
    public SecretIdentityDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<SecretIdentity> findBySecretName(String secretName){
        TypedQuery<SecretIdentity> query = entityManager.createQuery("FROM SecretIdentity WHERE secretName=:queryInput", SecretIdentity.class);
        query.setParameter("queryInput", secretName);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(long id) {
        SecretIdentity entry = entityManager.find(SecretIdentity.class, id);
        entry.getPerson().setSecretIdentity(null);
        entityManager.remove(entry);
    }
}
