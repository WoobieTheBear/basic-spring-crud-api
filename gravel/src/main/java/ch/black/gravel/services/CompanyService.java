package ch.black.gravel.services;

import java.util.List;

import ch.black.gravel.entities.Company;

public interface CompanyService {
    public Company findById(long id);
    public List<Company> findAll();
    public void save(Company company);
    public Company update(Company company);
    public void delete(long id);
    public void deleteAll();
}
