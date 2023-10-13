package com.example.pokedex.activities;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokedex.R;
import com.example.pokedex.adapters.ListPokemonAdapter;
import com.example.pokedex.adapters.ListSpritesAdapter;
import com.example.pokedex.models.Pokemon;
import com.example.pokedex.models.PokemonAbility;
import com.example.pokedex.models.PokemonDetail;
import com.example.pokedex.models.PokemonMove;
import com.example.pokedex.models.PokemonResponse;
import com.example.pokedex.models.PokemonSprites;
import com.example.pokedex.models.PokemonType;
import com.example.pokedex.pokeapi.PokemonService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private ListSpritesAdapter listSpritesAdapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();
        ImageView pokeImage = findViewById(R.id.imagePoke);
        setTitle("#" + extras.get("id") + " " + ((String) extras.get("name")).toUpperCase(Locale.ROOT) );
        Glide.with(this).load(extras.get("image"))
                .centerCrop().crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(pokeImage);
        retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();

        recyclerView = (RecyclerView) findViewById(R.id.recylerSprites);
        listSpritesAdapter = new ListSpritesAdapter(this);


        getPokemonDetail((int) extras.get("id"), this);
        recyclerView.setAdapter(listSpritesAdapter);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



    }



    private void getPokemonDetail(int pokemonId, Context context){
        PokemonService service = retrofit.create(PokemonService.class);
        Call<PokemonDetail> pokemonDetailCall =  service.getPokemonDetail(pokemonId);
        pokemonDetailCall.enqueue(new Callback<PokemonDetail>() {
            @Override
            public void onResponse(Call<PokemonDetail> call, Response<PokemonDetail> response) {
                if (response.isSuccessful()){

                    PokemonDetail pokemonDetail = response.body();
                    TextView weight = findViewById(R.id.weight);

                    pokemonDetail.getSprites();
                    showPokemonTypes(pokemonDetail.getTypes(), context);
                    showPokemonMoves(pokemonDetail.getMoves(), context);
                    showPokemonAbilities(pokemonDetail.getAbilities(), context);

                    weight.setText(pokemonDetail.getWeight() + " KG");


                }else{
                    Log.e(TAG, "on response" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonDetail> call, Throwable t) {
                Log.e(TAG, "on failure" + t.getMessage());
            }
        });

    }

    private void showPokemonTypes( ArrayList<PokemonType> pokemonTypes, Context context){
        LinearLayout horizontalList = findViewById(R.id.typeList);
        for(PokemonType type :pokemonTypes) {
            TextView textView = new TextView(context);
            textView.setText(type.getType().getName());
            textView.setTextSize(20);
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corner) );
            textView.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,0,15,0);
            textView.setLayoutParams(lp);
            textView.setPadding(18,14,18,14);
            horizontalList.addView(textView);
        }
    }

    private void showPokemonMoves( ArrayList<PokemonMove> pokemonMoves, Context context){
        LinearLayout horizontalListMove = findViewById(R.id.moveList);
        for(PokemonMove move :pokemonMoves) {
            TextView textView = new TextView(context);

            textView.setText(move.getMove().getName());
            textView.setTextSize(20);
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corner) );
            textView.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30,0,15,0);
            textView.setLayoutParams(lp);

            textView.setPadding(18,14,18,14);
            horizontalListMove.addView(textView);
        }
    }

    private void showPokemonAbilities(ArrayList<PokemonAbility> pokemonAbilities, Context context){
        LinearLayout horizontalListMove = findViewById(R.id.abilitiesList);
        for(PokemonAbility pokemonAbility :pokemonAbilities) {
            TextView textView = new TextView(context);

            textView.setText(pokemonAbility.getAbility().getName());
            textView.setTextSize(20);
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corner) );
            textView.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30,0,15,0);
            textView.setLayoutParams(lp);

            textView.setPadding(18,14,18,14);
            horizontalListMove.addView(textView);
        }
    }

    private void showSprites (PokemonSprites pokemonSprites, Context context) {

        ArrayList<String> sprites = new ArrayList<>();



    }


}
