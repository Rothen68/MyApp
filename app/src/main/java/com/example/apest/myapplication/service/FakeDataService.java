package com.example.apest.myapplication.service;

import android.content.res.Resources;

import com.example.apest.myapplication.model.Pet;
import com.example.apest.myapplication.model.PetType;

import java.util.ArrayList;

/**
 * Created by apest on 07/12/2016.
 */

public class FakeDataService implements DataService {

    ArrayList<Pet> sourceList;

    public FakeDataService() {
        this.sourceList = new ArrayList<>();
        this.sourceList.add(new Pet(1, PetType.CAT, "Garfield", 51));
        this.sourceList.add(new Pet(2, PetType.DOG, "Rantamplan", 32));
        this.sourceList.add(new Pet(3, PetType.PARROT, "Coco", 1));
    }

    @Override
    public int getAvailableCount() {
        return sourceList.size();
    }

    @Override
    public Pet getItemAt(int position) {
        return sourceList.get(position);
    }

    @Override
    public Pet getItemById(int id) {
        for(int i = 0 ; i < sourceList.size() ; i++)
        {
            if(sourceList.get(i).getId() == id)
            {
                return sourceList.get(i);
            }
        }
        throw new Resources.NotFoundException("Pet " + id + " not found");
    }

    @Override
    public void deleteItemById(int id) {
        for(int i = 0 ; i < sourceList.size() ; i++)
        {
            if(sourceList.get(i).getId() == id)
            {
                sourceList.remove(i);
                return;
            }
        }
        throw new Resources.NotFoundException("Pet " + id + " not found");
    }

    @Override
    public void setItemById(int id, Pet pet) {
        for(int i = 0 ; i < sourceList.size() ; i++)
        {
            if(sourceList.get(i).getId() == id)
            {
                sourceList.set(i, pet);
                return;
            }
        }
        throw new Resources.NotFoundException("Pet " + id + " not found");
    }

    @Override
    public int addPet(Pet pet) {
        int id = sourceList.get(sourceList.size()-1).getId();
        id++;
        pet.setId(id);
        sourceList.add(pet);
        return id;
    }
}
