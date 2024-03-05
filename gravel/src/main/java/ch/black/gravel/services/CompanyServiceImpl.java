package ch.black.gravel.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.black.gravel.repositories.CompanyRepository;
import ch.black.gravel.entities.Company;

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
        if (company != null){
            companyRepository.save(company);
        }
    }

    @Override
    public Company update(Company company) {
        if (company != null) {
            return companyRepository.save(company);
        } else {
            return null;
        }
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
