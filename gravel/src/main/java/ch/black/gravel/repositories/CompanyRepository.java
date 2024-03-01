package ch.black.gravel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.black.gravel.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> { }
