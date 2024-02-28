package io.black.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.black.rest.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> { }
