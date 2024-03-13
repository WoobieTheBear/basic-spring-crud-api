package ch.black.gravel.daos;

import java.util.List;

import ch.black.gravel.entities.SecretIdentity;

public interface SecretIdentityDAO {
    public List<SecretIdentity> findBySecretName(String secretName);
    public void delete(long id);
}
