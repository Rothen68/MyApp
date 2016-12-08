package com.example.apest.myapplication.model;

import android.media.Image;

public class Pet {

    int id;
    PetType type;
    String name;
    int age;

    public Pet(int id, PetType type, String name, int age) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
