package ch.black.gravel.entities;

import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="person")
public class Person {
    public Person(){}
    public Person(String firstName, String lastName, String email){
        this.fristName = firstName;
        this.lastName = lastName;
        this.email = email;
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        this.created = (this.created == null) ? now : this.created;
        this.lastLogin = now;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="first_name")
    private String fristName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="created_at")
    private Timestamp created;

    @Column(name="last_login")
    private Timestamp lastLogin;

    public long getId() {
        return id;
    }

    public void setId(long value) {
        id = value;
    }

    public String getFirstname() {
        return fristName;
    }

    public void setFirstname(String name) {
        fristName = name;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String name) {
        lastName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreationstamp() {
        return created;
    }

    public void setCreationstamp(Timestamp stamp) {
        this.created = stamp;
    }

    public Timestamp getLastlogin() {
        return lastLogin;
    }

    public void setLastlogin(Timestamp stamp) {
        this.lastLogin = stamp;
    }
    @Override
    public String toString() {
        return "[id=" + id + ", fristName=" + fristName + ", lastName=" + lastName + ", email=" + email + "]";
    }

}
