package ch.black.gravel.dtos;

import java.util.ArrayList;
import java.util.List;

import ch.black.gravel.entities.Pet;

public class PetsDTO {

    private long id = 0;

    private List<Pet> pets;

    public PetsDTO() {}

    public PetsDTO(List<Pet> pets) {
        this.pets = pets;
    }

    public void addTitle(Pet pet) {
        if (pets == null) {
            pets = new ArrayList<>();
        }
        this.pets.add(pet);
    }

    public void removeTitle(Integer index) {
        this.pets.remove((int) index);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public String toString() {
        return "[id = " + id + ", pets = " + pets + "]";
    }

}
