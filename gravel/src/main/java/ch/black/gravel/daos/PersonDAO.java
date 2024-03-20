package ch.black.gravel.daos;

import java.util.List;

import ch.black.gravel.entities.Person;

public interface PersonDAO {
    public void save(Person person);
    public Person update(Person person);
    public int updateMany(String updateColumn, String updateValue, String whereColumn, String whereValue);
    public void delete(long id);
    public int deleteMany(String whereColumn, String whereValue);
    public int deleteAll();
    public Person findById(long id);
    public Person loadArticlesEagerByPersonId(long id);
    public List<Person> findAll();
    public List<Person> findByLastName(String lastName);
    public Person findByFullName(String firstName, String lastName);
}
