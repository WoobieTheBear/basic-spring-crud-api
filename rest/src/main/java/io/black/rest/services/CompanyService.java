package io.black.rest.services;

import java.util.List;

import io.black.rest.entities.Company;

public interface CompanyService {
    public Company findById(long id);
    public List<Company> findAll();
    public void save(Company company);
    public Company update(Company company);
    public void delete(long id);
    public void deleteAll();
}
