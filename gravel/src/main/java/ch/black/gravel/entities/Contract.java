package ch.black.gravel.entities;

import ch.black.gravel.dtos.ContractDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "workcontract", schema = "black_data")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "contract_workload")
    private Integer contractWorkload;

    @Column(name = "salary")
    private Integer salary;

    @ManyToOne
    @JoinColumn(name = "company_id")
    Company company;

    @ManyToOne
    @JoinColumn(name = "person_id")
    Person person;

    public Contract() {}

    public Contract(String title, Integer contractWorkload, Integer salary) {
        this.title = title;
        this.contractWorkload = contractWorkload;
        this.salary = salary;
    }

    public Contract(ContractDTO contractDTO) {
        if (contractDTO != null) {
            id = contractDTO.getId();
            title = contractDTO.getTitle();
            contractWorkload = contractDTO.getContractWorkload();
            salary = contractDTO.getSalary();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getContractWorkload() {
        return contractWorkload;
    }

    public void setContractWorkload(Integer contractWorkload) {
        this.contractWorkload = contractWorkload;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", title=" + title + ", contractWorkload=" + contractWorkload + ", salary=" + salary + "]";
    }
    
}
