package ch.black.gravel.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.black.gravel.daos.PersonDAO;
import ch.black.gravel.entities.Person;
import jakarta.transaction.Transactional;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonDAO personDAO;

    @Autowired
    public PersonServiceImpl(
        PersonDAO injectedPersonDAO
    ) {
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
    public Person loadArticlesEagerByPersonId(long id) {
        return personDAO.loadArticlesEagerByPersonId(id);
    }

    @Override
    public List<Person> findByLastName(String lastName) {
        return personDAO.findByLastName(lastName);
    }

    @Override
    public Person findByFullName(String firstName, String lastName) {
        return personDAO.findByFullName(firstName, lastName);
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
