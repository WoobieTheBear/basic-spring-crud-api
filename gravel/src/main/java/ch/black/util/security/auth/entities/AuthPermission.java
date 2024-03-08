package ch.black.util.security.auth.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "AuthPermission")
@Table(name = "auth_permission")
public class AuthPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "permission_name")
    private String permissionName;

    public AuthPermission() {
    }

    public AuthPermission(String name) {
        this.permissionName = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String name) {
        this.permissionName = name;
    }

    @Override
    public String toString() {
        return "{ \"id\": \"" + id + "\", \"name\": \"" + permissionName + "\" }";
    }

}
