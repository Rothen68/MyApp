package com.example.apest.myapplication.service;

import com.example.apest.myapplication.model.Pet;
import com.example.apest.myapplication.model.PetType;

/**
 * Created by apest on 07/12/2016.
 */

public interface DataService {

    int getAvailableCount();
    Pet getItemAt(int position);
    Pet getItemById(int id);

    void deleteItemById(int id);
    void setItemById(int petId, Pet pet);

    int addPet(Pet pet);

}
