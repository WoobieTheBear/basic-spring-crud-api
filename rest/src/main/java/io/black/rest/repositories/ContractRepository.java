package io.black.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import io.black.rest.entities.Contract;

@RepositoryRestResource(path="workcontracts")
public interface ContractRepository extends JpaRepository<Contract, Long> { }
