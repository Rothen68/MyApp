package com.example.apest.myapplication.service;

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
    public void setItem(Pet pet, int position) {
        sourceList.set(position,pet);
    }
}
