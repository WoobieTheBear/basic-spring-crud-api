package ch.black.util.security.auth.daos;

import ch.black.util.security.auth.entities.AuthEntity;

public interface AuthEntityDAO {
    
    AuthEntity findEntityByName(String searchName);

    void save(AuthEntity entity);

}
