package ch.black.gravel.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "company", schema = "black_data")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="company_name")
    private String companyName;

    @Column(name="contact_email")
    private String contactEmail;

    @JsonIgnore
    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "company",
        cascade = CascadeType.ALL
    )
    private List<Contract> contracts;

    public Company() {}

    public Company(String companyName, String contactEmail) {
        this.companyName = companyName;
        this.contactEmail = contactEmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", companyName=" + companyName + ", contactEmail=" + contactEmail + "]";
    }

}
