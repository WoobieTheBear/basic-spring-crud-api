package ch.black.util.security.auth.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignupFormDTO {
    
    @NotNull(message = "The email is required")
    @Pattern(
        message = "Please enter a valid email adress",
        regexp = "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$"
    )
    private String email;
    
	@NotNull(message = "The name is required")
	@Size(min = 3, message = "The name needs at least 3 characters")
    private String name;

	@NotNull(message = "The password is required")
	@Size(min = 3, message = "The password needs at least 9 characters")
    private String password;

    public SignupFormDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignupFormDTO [email=" + email + ", name=" + name + ", password=" + password + "]";
    }

}
