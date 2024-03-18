package ch.black.gravel.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ch.black.gravel.entities.Person;
import ch.black.gravel.entities.Pet;

@Repository
public class PersonDAOImpl implements PersonDAO {
    private EntityManager entityManager;

    @Autowired
    public PersonDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void save(Person person){
        this.entityManager.persist(person);
    }

    @Override
    public Person update(Person person){
        return this.entityManager.merge(person);
    }

    @Override
    public int updateMany(String updateColumn, String updateValue, String whereColumn, String whereValue){
        String queryString = "UPDATE Person SET " + updateColumn + " = :updateValue WHERE " + whereColumn + " = :whereValue";
        return entityManager.createQuery(
            queryString
        ).setParameter("updateValue", updateValue)
        .setParameter("whereValue", whereValue)
        .executeUpdate();
    }

    @Override
    public void delete(long id){
        Person toBedeleted = entityManager.find( Person.class, id);
        for (Pet title : toBedeleted.getPets()) {
            /*  [NOTE]: if the referenced dataset is still in use you only set
                        the back reference to null on the dataset
                        in this example it would be the person
                        like in it would be done in the commented line
            */
            // title.setPerson(null);
            entityManager.remove(title);
        }
        entityManager.remove(toBedeleted);
    }

    @Override
    public int deleteMany(String whereColumn, String whereValue){
        /* this method assumes all references have been deleted */
        String queryString = "DELETE Person WHERE " + whereColumn + " = :whereValue";
        return entityManager.createQuery(
            queryString
        ).setParameter("whereValue", whereValue)
        .executeUpdate();
    }

    @Override
    public int deleteAll(){
        /* this method assumes all references have been deleted */
        return entityManager.createQuery("DELETE FROM Person").executeUpdate();
    }

    @Override
    public Person findById(long id){
        return this.entityManager.find(Person.class, id);
    }

    @Override
    public List<Person> findAll(){
        TypedQuery<Person> query = entityManager.createQuery("FROM Person order by lastName", Person.class);
        return query.getResultList();
    }

    @Override
    public List<Person> findByLastName(String lastName){
        TypedQuery<Person> query = entityManager.createQuery("FROM Person WHERE lastName=:queryInput", Person.class);
        query.setParameter("queryInput", lastName);
        return query.getResultList();
    }
}
