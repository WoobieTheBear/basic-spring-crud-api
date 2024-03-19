package ch.black.gravel.dtos;

import java.util.List;

import ch.black.gravel.entities.Company;
import ch.black.gravel.entities.Contract;
import ch.black.gravel.entities.Person;

public class ContractDTO {

    private long id = 0;

    private String title;

    private Integer salary;

    private Integer contractWorkload;

    private Long personId;

    private List<Person> people;

    private Long companyId;

    private List<Company> companies;

    public ContractDTO() {}

    public ContractDTO(Contract contract) {
        if (contract != null) {
            id = contract.getId();
            personId = (contract.getPerson() != null) ? contract.getPerson().getId() : 0;
            companyId = (contract.getCompany() != null) ? contract.getCompany().getId() : 0;
            title = contract.getTitle();
            salary = contract.getSalary();
            contractWorkload = contract.getContractWorkload();
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

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getContractWorkload() {
        return contractWorkload;
    }

    public void setContractWorkload(Integer workload) {
        this.contractWorkload = workload;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

}
