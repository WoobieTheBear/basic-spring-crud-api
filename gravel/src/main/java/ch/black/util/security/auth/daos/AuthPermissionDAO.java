package ch.black.util.security.auth.daos;

import ch.black.util.security.auth.entities.AuthPermission;

public interface AuthPermissionDAO {
    
    public AuthPermission findPermissionByName(String name);

}
