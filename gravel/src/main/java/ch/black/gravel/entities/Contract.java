package ch.black.gravel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "workcontract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "contract_workload")
    private Integer contractWorkload;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "company_id")
    private Long companyId;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "company_id", referencedColumnName = "id")
    Company company;

    @Column(name = "person_id")
    private Long personId;

    @ManyToOne
    @JoinColumn(insertable = false, updatable = false, name = "person_id", referencedColumnName = "id")
    Person person;

    public Contract() {}

    public Contract(Integer contractWorkload, Integer salary, Long companyId, Long personId) {
        this.contractWorkload = contractWorkload;
        this.salary = salary;
        this.companyId = companyId;
        this.personId = personId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Company getCompany() {
        return company;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", contractWorkload=" + contractWorkload + ", salary=" + salary + ", companyId="
                + companyId + ", personId=" + personId + "]";
    }
    
}
