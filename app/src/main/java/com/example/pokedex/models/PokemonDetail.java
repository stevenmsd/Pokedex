package com.example.pokedex.models;

import com.example.pokedex.models.PokemonAbility;
import com.example.pokedex.models.PokemonSprites;
import com.example.pokedex.models.PokemonType;
import com.example.pokedex.models.PokemonMove;
import java.util.ArrayList;


public class PokemonDetail {
    private String weight;
    private String height;
    private ArrayList<PokemonType> types;
    private PokemonSprites sprites;
    private ArrayList<PokemonAbility> abilities;
    private ArrayList<PokemonMove> moves;

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public ArrayList<PokemonType> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<PokemonType> types) {
        this.types = types;
    }

    public PokemonSprites getSprites() {
        return sprites;
    }

    public void setSprites(PokemonSprites sprites) {
        this.sprites = sprites;
    }

    public ArrayList<PokemonAbility> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<PokemonAbility> abilities) {
        this.abilities = abilities;
    }

    public ArrayList<PokemonMove> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<PokemonMove> moves) {
        this.moves = moves;
    }
}