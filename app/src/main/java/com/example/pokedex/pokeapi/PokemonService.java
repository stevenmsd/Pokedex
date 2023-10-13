package com.example.pokedex.pokeapi;

import com.example.pokedex.models.PokemonDetail;
import com.example.pokedex.models.PokemonResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonService {
    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}")
    Call<PokemonDetail> getPokemonDetail(@Path("id") int pokemonId);
}
