package com.gpp.rickandmorty;

public class MyCharacter {

    private final String name;
    private final String status;
    private final String species;

    private final String type;

    private final String gender;

    private final String image;

    public MyCharacter(String name, String status, String species, String type, String gender, String image) {
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public String getImage() {
        return image;
    }
}
