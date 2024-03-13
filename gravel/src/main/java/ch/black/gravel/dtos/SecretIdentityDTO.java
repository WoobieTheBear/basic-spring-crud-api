package ch.black.gravel.dtos;

import ch.black.gravel.entities.SecretIdentity;

public class SecretIdentityDTO {

    private long id = 0;

    private String secretName = "";

    public SecretIdentityDTO() {}

    public SecretIdentityDTO(String secretName) {
        this.secretName = secretName;
    }

    public SecretIdentityDTO(SecretIdentity identity) {
        if (identity != null) {
            this.id = identity.getId();
            this.secretName = identity.getSecretName();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
    }

    @Override
    public String toString() {
        return "[id = " + id + ", secretName = " + secretName + "]";
    }

}
