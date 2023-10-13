package com.example.pokedex.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokedex.R;
import com.example.pokedex.activities.DetailActivity;
import com.example.pokedex.models.Pokemon;

import java.util.ArrayList;

public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder> {

    private ArrayList<Pokemon> dataset;
    private Context context;
    private Pokemon pokemon;


    public ListPokemonAdapter (Context context) {
        this.context = context;
        dataset = new ArrayList<>();

    }


    @NonNull
    @Override
    public ListPokemonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPokemonAdapter.ViewHolder holder, int position) {
        pokemon = dataset.get(position);
        holder.nameTextView.setText( "#"+pokemon.getNumber()+ " " + " " + pokemon.getName());
        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ pokemon.getNumber()+".png")
                .centerCrop().crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.pictureImageView);



    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView pictureImageView;
        private TextView nameTextView;
        private CardView cards;

        public ViewHolder(View itemView){
            super(itemView);

            pictureImageView = (ImageView) itemView.findViewById(R.id.pictureImageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            cards = (CardView) itemView.findViewById(R.id.cards);

            cards.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.cards:
                    Pokemon poke = dataset.get(getAdapterPosition());

                    Intent i = new Intent(view.getContext(), DetailActivity.class);
                    i.putExtra("name",poke.getName());
                    i.putExtra("id",poke.getNumber());
                    i.putExtra("image","https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ poke.getNumber()+".png");
                    view.getContext().startActivity(i);

                    break;
            }

        }
    }

    public void addList( ArrayList<Pokemon> pokemonList){
        dataset.addAll(pokemonList);
        notifyDataSetChanged();
    }


}


