package com.example.pokedex.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokedex.R;
import com.example.pokedex.models.Pokemon;

import java.util.ArrayList;


public class ListSpritesAdapter extends RecyclerView.Adapter<ListSpritesAdapter.ViewHolder> {

    private ArrayList<String> dataset;
    private Context context;

    public ListSpritesAdapter(Context context){
        this.context = context;
        dataset = new ArrayList<>();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sprite_item_pokemon, parent, false);
        return new ListSpritesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(dataset.get(position))
                .centerCrop().crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.spriteImageView);

    }

    @Override
    public int getItemCount() {
         return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView spriteImageView;

        public ViewHolder(View itemView){
            super(itemView);
            spriteImageView = (ImageView) itemView.findViewById(R.id.spriteImageView);

        }

    }

    public void addList( ArrayList<String> spriteList){
        dataset.addAll(spriteList);
        notifyDataSetChanged();
    }

}
