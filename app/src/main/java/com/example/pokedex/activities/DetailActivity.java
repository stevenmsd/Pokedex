package com.example.pokedex.activities;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokedex.R;
import com.example.pokedex.models.Pokemon;
import com.example.pokedex.models.PokemonDetail;
import com.example.pokedex.models.PokemonResponse;
import com.example.pokedex.pokeapi.PokemonService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private Retrofit retrofit;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();
        ImageView pokeImage = findViewById(R.id.imagePoke);
        setTitle("#" + extras.get("id") + " " + extras.get("name") );
        Glide.with(this).load(extras.get("image"))
                .centerCrop().crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(pokeImage);
        getPokemonDetail(Integer.parseInt((String) extras.get("id")));
    }



    private void getPokemonDetail(int pokemonId){
        PokemonService service = retrofit.create(PokemonService.class);
        Call<PokemonDetail> pokemonDetailCall =  service.getPokemonDetail(pokemonId);
        pokemonDetailCall.enqueue(new Callback<PokemonDetail>() {
            @Override
            public void onResponse(Call<PokemonDetail> call, Response<PokemonDetail> response) {
                if (response.isSuccessful()){
                    System.out.println("Correctooooo");
                    PokemonDetail pokemonDetail = response.body();

                }else{
                    System.out.println("No correctoooooooooo");
                    Log.e(TAG, "on response" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonDetail> call, Throwable t) {
                Log.e(TAG, "on failure" + t.getMessage());
            }
        });

    }
}
