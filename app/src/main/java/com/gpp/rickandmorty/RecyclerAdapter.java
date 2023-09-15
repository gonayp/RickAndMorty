package com.gpp.rickandmorty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    Context context;
    List<MyCharacter> list;

    public RecyclerAdapter(Context context, List<MyCharacter> list) {
        this.context = context;
        this.list = list;
    }

    public void addData(List<MyCharacter> list){
        this.list.addAll(list);
    }

    public void deleteData(){
        this.list.clear();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_character,viewGroup,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.getNombre().setText(list.get(i).getName());
        holder.getStatus().setText(list.get(i).getStatus());
        holder.getSpecies().setText(list.get(i).getSpecies());
        holder.getType().setText(list.get(i).getType());
        holder.getGender().setText(list.get(i).getGender());
        Glide.with(context)
                .load(list.get(i).getImage())
                .transition(DrawableTransitionOptions.withCrossFade()) // Transición (opcional)
                .into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView nombre, status,species,gender,type;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgPhoto);
            nombre = itemView.findViewById(R.id.tvName);
            status = itemView.findViewById(R.id.tvStatus);
            species = itemView.findViewById(R.id.tvSpice);
            gender = itemView.findViewById(R.id.tvGender);
            type = itemView.findViewById(R.id.tvType);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getNombre() {
            return nombre;
        }

        public TextView getSpecies() {
            return species;
        }

        public TextView getStatus() {
            return status;
        }

        public TextView getGender() {
            return gender;
        }

        public TextView getType() {
            return type;
        }
    }
}
