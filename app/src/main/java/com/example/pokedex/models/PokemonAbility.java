package com.example.pokedex.models;

public class PokemonAbility {
    private Ability ability;
    private boolean isHidden;
    private int slot;

    public Ability getAbility() {
        return ability;
    }


    public boolean isHidden() {
        return isHidden;
    }

    public int getSlot() {
        return slot;
    }


    public class Ability {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }





}
