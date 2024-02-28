package io.black.rest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "workcontract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "contract_workload")
    private int contractWorkload;

    @Column(name = "salary")
    private int salary;

    @Column(name = "company_id")
    private int companyId;

    @Column(name = "person_id")
    private int personId;

    public Contract() {}

    public Contract(int contractWorkload, int salary, int companyId, int personId) {
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

    public int getContractWorkload() {
        return contractWorkload;
    }

    public void setContractWorkload(int contractWorkload) {
        this.contractWorkload = contractWorkload;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", contractWorkload=" + contractWorkload + ", salary=" + salary + ", companyId="
                + companyId + ", personId=" + personId + "]";
    }
    
}
