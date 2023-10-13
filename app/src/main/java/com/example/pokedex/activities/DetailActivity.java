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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokedex.R;
import com.example.pokedex.models.Pokemon;
import com.example.pokedex.models.PokemonDetail;
import com.example.pokedex.models.PokemonMove;
import com.example.pokedex.models.PokemonResponse;
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
    private RecyclerView recyclerViewListType;

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






        retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        getPokemonDetail((int) extras.get("id"), this);

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


                    LinearLayout horizontalList = findViewById(R.id.typeList);

                    for(PokemonType type :pokemonDetail.getTypes()) {
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

                    LinearLayout horizontalListMove = findViewById(R.id.moveList);
                    System.out.println(pokemonDetail.getMoves().size());
                    for(PokemonMove move :pokemonDetail.getMoves()) {
                        TextView textView = new TextView(context);
                        System.out.println(move.getMove().getName());

                        textView.setText(move.getMove().getName());
                        textView.setTextSize(20);
                        textView.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corner) );
                        textView.setTextColor(Color.WHITE);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(0,0,15,0);
                        textView.setLayoutParams(lp);

                        textView.setPadding(18,14,18,14);
                        horizontalListMove.addView(textView);
                    }




                    weight.setText(pokemonDetail.getWeight() + " KG");


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
