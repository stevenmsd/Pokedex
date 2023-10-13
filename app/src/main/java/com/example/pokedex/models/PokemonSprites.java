package com.example.pokedex.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PokemonSprites {
    @SerializedName("back_default")
    private String backDefault;

    @SerializedName("back_female")
    private String backFemale;

    @SerializedName("back_shiny")
    private String backShiny;

    @SerializedName("back_shiny_female")
    private String backShinyFemale;

    @SerializedName("front_default")
    private String frontDefault;

    @SerializedName("front_female")
    private String frontFemale;

    @SerializedName("front_shiny")
    private String frontShiny;

    @SerializedName("front_shiny_female")
    private String frontShinyFemale;

    public ArrayList<String> getImagesSprites (){
        ArrayList<String> filteredAttributes = new ArrayList<>();

        if (this.getBackDefault() != null) {
            filteredAttributes.add(this.getBackDefault());
        }
        if (this.getBackFemale() != null) {
            filteredAttributes.add(this.getBackFemale());
        }
        if (this.getBackShiny() != null) {
            filteredAttributes.add(this.getBackShiny());
        }
        if (this.getBackShinyFemale() != null) {
            filteredAttributes.add(this.getBackShinyFemale());
        }
        if (this.getFrontDefault() != null) {
            filteredAttributes.add(this.getFrontDefault());
        }
        if (this.getFrontFemale() != null) {
            filteredAttributes.add(this.getFrontFemale());
        }
        if (this.getFrontShiny() != null) {
            filteredAttributes.add(this.getFrontShiny());
        }
        if (this.getFrontShinyFemale() != null) {
            filteredAttributes.add(this.getFrontShinyFemale());
        }

        return filteredAttributes;
    }


    public String getBackDefault() {
        return backDefault;
    }

    public String getBackFemale() {
        return backFemale;
    }

    public String getBackShiny() {
        return backShiny;
    }

    public String getBackShinyFemale() {
        return backShinyFemale;
    }

    public String getFrontDefault() {
        return frontDefault;
    }

    public String getFrontFemale() {
        return frontFemale;
    }

    public String getFrontShiny() {
        return frontShiny;
    }

    public String getFrontShinyFemale() {
        return frontShinyFemale;
    }


}