package com.example.apest.myapplication.model;

/**
 * Created by apest on 07/12/2016.
 */

public enum PetType {
    UNKNOWN("unknown"),
    CAT("cat"),
    DOG("dog"),
    PARROT("parrot");

    protected String label;

    PetType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
