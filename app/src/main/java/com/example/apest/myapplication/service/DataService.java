package com.example.apest.myapplication.service;

import com.example.apest.myapplication.model.Pet;

/**
 * Created by apest on 07/12/2016.
 */

public interface DataService {

    int getAvailableCount();
    Pet getItemAt(int position);
    void setItem(Pet pet, int position);
}
