package ch.black.gravel.entities;

import java.sql.Timestamp;
import java.util.Date;

import ch.black.gravel.dtos.PersonDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "person", schema = "black_data")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="created_at")
    private Timestamp created;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "secret_identity_id")
    private SecretIdentity secretIdentity;

    public Person(){}

    public Person(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        Timestamp now = new Timestamp(new Date().getTime());
        this.created = (this.created == null) ? now : this.created;
    }

    public Person(PersonDTO personDTO) {
        this.mergeDTO(personDTO);
    }

    public void mergeDTO(PersonDTO personDTO) {
        this.firstName = personDTO.getFirstName();
        this.lastName = personDTO.getLastName();
        this.email = personDTO.getEmail();
    }

    public long getId() {
        return id;
    }

    public void setId(long value) {
        id = value;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        lastName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public SecretIdentity getSecretIdentity() {
        return secretIdentity;
    }

    public void setSecretIdentity(SecretIdentity secretIdentity) {
        this.secretIdentity = secretIdentity;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", secretIdentity=" + secretIdentity + "]";
    }

}
