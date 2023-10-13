package com.example.pokedex.models;

public class PokemonType {
    private int slot;
    private Type type;

    public int getSlot() {
        return slot;
    }

    public Type getType() {
        return type;
    }

    public class Type {
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

