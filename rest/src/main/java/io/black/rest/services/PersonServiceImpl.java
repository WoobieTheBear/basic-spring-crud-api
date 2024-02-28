package io.black.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.black.rest.daos.PersonDAO;
import io.black.rest.entities.Person;
import jakarta.transaction.Transactional;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonDAO personDAO;

    @Autowired
    public PersonServiceImpl(PersonDAO injectedPersonDAO) {
        this.personDAO = injectedPersonDAO;
    }

    @Override
    public List<Person> findAll() {
        return personDAO.findAll();
    }

    @Override
    public Person findById(long id) {
        return personDAO.findById(id);
    }

    @Override
    public List<Person> findByLastName(String lastName) {
        return personDAO.findByLastName(lastName);
    }

    @Override
    @Transactional
    public void save(Person person) {
        personDAO.save(person);
    }

    @Override
    @Transactional
    public Person update(Person person) {
        return personDAO.update(person);
    }

    @Override
    @Transactional
    public int updateMany(String updateColumn, String updateValue, String whereColumn, String whereValue) {
        return personDAO.updateMany(updateColumn, updateValue, whereColumn, whereValue);
    }

    @Override
    @Transactional
    public void delete(long id) {
        personDAO.delete(id);
    }

    @Override
    @Transactional
    public int deleteAll() {
        return personDAO.deleteAll();
    }

    @Override
    @Transactional
    public int deleteMany(String whereColumn, String whereValue) {
        return personDAO.deleteMany(whereColumn, whereValue);
    }

}
