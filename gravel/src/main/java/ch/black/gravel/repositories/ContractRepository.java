package ch.black.gravel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ch.black.gravel.entities.Contract;

@RepositoryRestResource(path = "workcontracts")
public interface ContractRepository extends JpaRepository<Contract, Long> {
    public List<Contract> findAllByOrderBySalaryDesc();
}
