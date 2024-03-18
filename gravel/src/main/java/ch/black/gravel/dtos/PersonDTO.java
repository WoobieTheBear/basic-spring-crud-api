package ch.black.gravel.dtos;

import java.util.List;

import ch.black.gravel.entities.Person;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PersonDTO {

    private long id = 0;

	@NotNull(message = "The first name is required")
	@Size(min = 3, message = "The first name needs at least 3 characters")
    private String firstName = "";

	@NotNull(message = "The last name is required")
	@Size(min = 3, message = "The last name needs at least 3 characters")
    private String lastName = "";

    private List<String> titles;

    @NotNull(message = "The email is required")
    @Pattern(
        message = "Please enter a valid email adress",
        regexp = "^[\\w\\-\\.]+@([\\w-]+\\.){0,32}+[\\w-]{2,}$"
    )
    private String email = "";

    public PersonDTO() {}

    public PersonDTO(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public PersonDTO(Person person) {
        if (person != null) {
            this.id = person.getId();
            this.firstName = person.getFirstName();
            this.lastName = person.getLastName();
            this.email = person.getEmail();
        }
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

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
    }

}
