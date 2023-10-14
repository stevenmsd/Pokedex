package com.example.pokedex.activities;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pokedex.R;
import com.example.pokedex.adapters.ListSpritesAdapter;
import com.example.pokedex.models.PokemonAbility;
import com.example.pokedex.models.PokemonDetail;
import com.example.pokedex.models.PokemonMove;
import com.example.pokedex.models.PokemonType;
import com.example.pokedex.pokeapi.PokemonService;
import com.google.android.material.appbar.AppBarLayout;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity  {

    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private ListSpritesAdapter listSpritesAdapter;
    private ConstraintLayout dataLayout;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_detail);

        dataLayout = findViewById(R.id.data);
        dataLayout.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Bundle extras = getIntent().getExtras();
        ImageView pokeImage = findViewById(R.id.imagePoke);
        setTitle("");

        TextView pokemonNumber = findViewById(R.id.pokemonId);
        TextView pokemonName = findViewById(R.id.pokemonName);

        pokemonNumber.setText("N°" + (int)extras.get("id"));
        pokemonName.setText((String)extras.get("name"));


        Glide.with(this)
                .asBitmap()
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + extras.get("id")+ ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Bitmap>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        // Manejar la carga fallida si es necesario
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        // Cuando la imagen se carga con éxito, obtenemos el color de la paleta
                        getPaletteColor(resource);
                        return false;
                    }
                })
                .into(pokeImage);


        retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        recyclerView = (RecyclerView) findViewById(R.id.recylerSprites);
        listSpritesAdapter = new ListSpritesAdapter(this);
        recyclerView.setAdapter(listSpritesAdapter);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


        getPokemonDetail((int) extras.get("id"), this);


    }



    private void getPokemonDetail(int pokemonId, Context context){
        PokemonService service = retrofit.create(PokemonService.class);
        Call<PokemonDetail> pokemonDetailCall =  service.getPokemonDetail(pokemonId);
        pokemonDetailCall.enqueue(new Callback<PokemonDetail>() {
            @Override
            public void onResponse(Call<PokemonDetail> call, Response<PokemonDetail> response) {
                if (response.isSuccessful()){
                    dataLayout.setVisibility(View.VISIBLE);
                    //ConstraintLayout progress = findViewById(R.id.loading);
                    //progress.setVisibility(View.GONE);
                    PokemonDetail pokemonDetail = response.body();
                    TextView weight = findViewById(R.id.weight);
                    TextView height = findViewById(R.id.height);
                    weight.setText(pokemonDetail.getWeight() + " KG");
                    height.setText(pokemonDetail.getHeight() + " m");
                    pokemonDetail.getSprites();
                    showPokemonTypes(pokemonDetail.getTypes(), context);
                    showPokemonMoves(pokemonDetail.getMoves(), context);
                    showPokemonAbilities(pokemonDetail.getAbilities(), context);
                    listSpritesAdapter.addList(pokemonDetail.getSprites().getImagesSprites());



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
            textView.setText(" " + type.getType().getName()+ " ");
            textView.setTextSize(16);
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corner_type) );
            textView.setTextColor(Color.BLACK);
            textView.setTypeface(null, Typeface.BOLD);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,0,15,0);
            textView.setLayoutParams(lp);
            textView.setPadding(20,20,20,20);
            horizontalList.addView(textView);
        }
    }

    private void showPokemonMoves( ArrayList<PokemonMove> pokemonMoves, Context context){
        LinearLayout horizontalListMove = findViewById(R.id.moveList);
        for(PokemonMove move :pokemonMoves) {
            TextView textView = new TextView(context);
            textView.setText(move.getMove().getName());
            textView.setTextSize(20);
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corner_type) );
            textView.setTextColor(Color.BLACK);
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
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corner_type) );
            textView.setTextColor(Color.BLACK);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30,0,15,0);
            textView.setLayoutParams(lp);

            textView.setPadding(18,14,18,14);
            horizontalListMove.addView(textView);
        }
    }

    private void getPaletteColor(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int dominantColor = palette.getDominantColor(Color.TRANSPARENT);

                // Obtén el fondo circular personalizado
                Drawable circleDrawable = ContextCompat.getDrawable(DetailActivity.this, R.drawable.semicircle_background);

                if (circleDrawable instanceof GradientDrawable) {
                    // Ajusta el color de relleno en el Drawable
                    ((GradientDrawable) circleDrawable).setColor(dominantColor);
                }

                // Aplica el fondo circular modificado como fondo del FrameLayout
                FrameLayout topLayout = findViewById(R.id.topLayout);
                topLayout.setBackground(circleDrawable);
            }
        });
    }





}
