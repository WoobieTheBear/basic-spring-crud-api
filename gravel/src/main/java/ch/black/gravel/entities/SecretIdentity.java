package ch.black.gravel.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.black.gravel.dtos.SecretIdentityDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "secret_identity", schema = "black_data")
public class SecretIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="secret_name")
    private String secretName;

    // "mappedBy" targets a property on the Person class
    @JsonIgnore
    @OneToOne(
    mappedBy = "secretIdentity",
    fetch = FetchType.LAZY,
    cascade = {
        // this will not delete the Person only the secret identity
        CascadeType.DETACH,
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH
    })
    private Person person;

    public SecretIdentity() {}

    public SecretIdentity(String secretName) {
        this.secretName = secretName;
    }

    public SecretIdentity(SecretIdentityDTO identityDTO) {
        this.mergeDTO(identityDTO);
    }

    public void mergeDTO(SecretIdentityDTO identityDTO) {
        this.secretName = identityDTO.getSecretName();
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "[id = " + id + ", secretName = " + secretName + "]";
    }

}
