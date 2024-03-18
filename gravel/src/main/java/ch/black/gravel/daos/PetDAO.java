package ch.black.gravel.daos;

import java.util.List;

import ch.black.gravel.entities.Pet;

public interface PetDAO {
    public List<Pet> findPetsByOwner(String firstName, String lastName);
    public List<Pet> findExisting(List<Pet> values);
    public List<Pet> findByName(String value);
    public void delete(long id);
}
