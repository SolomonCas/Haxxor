package com.example.haxxor.AbilityListView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haxxor.R;

import java.util.ArrayList;

public class ability_RecyclerAdaptor extends RecyclerView.Adapter<ability_RecyclerAdaptor.MyViewHolder> {

    Context context;
    ArrayList<Ability> ability_model;

    public ability_RecyclerAdaptor(Context context, ArrayList<Ability> ability_model) {
        this.context = context;
        this.ability_model = ability_model;
    }

    @NonNull
    @Override
    public ability_RecyclerAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ability_rows, parent, false);
        return new ability_RecyclerAdaptor.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ability_RecyclerAdaptor.MyViewHolder holder, int position) {
        holder.Ability_Name.setText(ability_model.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detailed_Ability.class);
                intent.putExtra("Name", ability_model.get(position).getName());
                intent.putExtra("Image", ability_model.get(position).getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ability_model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Ability_Name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Ability_Name = itemView.findViewById(R.id.Ability_Name);
        }
    }
}
