package ch.black.gravel.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ch.black.gravel.entities.Person;
import ch.black.gravel.entities.Pet;

@Repository
public class PetDAOImpl implements PetDAO {
    private EntityManager entityManager;

    @Autowired
    public PetDAOImpl(EntityManager injectedManager) {
        this.entityManager = injectedManager;
    }

    @Override
    public List<Pet> findPetsByOwner(String firstName, String lastName) {
        TypedQuery<Person> query = entityManager
            .createQuery(
                "SELECT p FROM Person p "
                + "JOIN FETCH p.pets "
                + "WHERE firstName=:firstInput AND lastName=:secondInput",
                Person.class
            )
            .setParameter("firstInput", firstName)
            .setParameter("secondInput", lastName);
        
        List<Person> result = query.getResultList();

        if (result.size() > 0) {
            return result.get(0).getPets();
        } else {
            return List.of();
        }
    }

    @Override
    public List<Pet> findByName(String value) {
        TypedQuery<Pet> query = entityManager
            .createQuery("FROM Pet WHERE name=:queryInput", Pet.class)
            .setParameter("queryInput", value);
        return query.getResultList();
    }

    @Override
    public List<Pet> findExisting(List<Pet> values) {
        String queryString = "FROM Pet WHERE ";
        int index = 1;
        for (Pet value : values) {
            queryString += "name='" + value.getName() + "' ";
            if (index < values.size()) {
                queryString += " OR ";
            }
            index ++;
        }
        System.out.println("findExisting() " + queryString);

        TypedQuery<Pet> query = entityManager.createQuery(queryString, Pet.class);
        
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(long id) {
        Pet entry = entityManager.find(Pet.class, id);
        entry.getOwner().removePet(id);
        entityManager.remove(entry);
    }
}
