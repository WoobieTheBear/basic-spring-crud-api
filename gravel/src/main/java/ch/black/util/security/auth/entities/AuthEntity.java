package ch.black.util.security.auth.entities;

import java.util.Collection;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity(name = "BlackAuthEntity")
@Table(name = "auth_entity", schema = "black_security")
public class AuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "entity_email")
    private String email;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_key")
    private String password;

    @Column(name = "entity_group")
    private Long group;

    @Column(name = "entity_active")
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "auth_access_tuple",
            schema = "black_security",
            joinColumns = @JoinColumn(name = "entity_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Collection<AuthPermission> permissions;

    public AuthEntity() {
    }

    public AuthEntity(String name, String password, boolean active) {
        this.entityName = name;
        this.password = password;
        this.active = active;
    }

    public AuthEntity(String name, String password, boolean active, Collection<AuthPermission> permissions) {
        this.entityName = name;
        this.password = password;
        this.active = active;
        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String newName) {
        this.entityName = newName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Collection<AuthPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<AuthPermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "{\"id\": \"" + id + "\", \"email\": \"" + email + "\", \"name\": \"" + entityName + "\", \"password\": \"" + password + 
            "\", \"group\": \"" + group + "\", \"active\": \"" + active + "\", \"permissions\": \"" + permissions + "\"}";
    }
    
}
