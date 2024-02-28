package io.black.rest.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.black.rest.repositories.CompanyRepository;
import io.black.rest.entities.Company;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository injectedRepository) {
        this.companyRepository = injectedRepository;
    }

    @Override
    public Company findById(long id) {
        Optional<Company> result = companyRepository.findById(id);
        Company company = null;
        if (result.isPresent()){
            company = result.get();
        }
        return company;
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public void save(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Company update(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void delete(long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        companyRepository.deleteAll();
    }

}
