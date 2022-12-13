package com.example.incubadora.modelos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.incubadora.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class adaptadormiincu extends RecyclerView.Adapter<adaptadormiincu.ViewHolder> {
    private List<incubadora> incuList;
    private Context context;

    public adaptadormiincu(Context context) {
        this.context = context;
        incuList = new ArrayList<>();
    }
    public adaptadormiincu(List<incubadora>pokemonList){this.incuList = pokemonList;}


    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.incuvadoras,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(incuList.get(position));
        incubadora incu = incuList.get(position);

    }

    @Override
    public int getItemCount() {
        return incuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Name;
        TextView Email;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=(TextView) itemView.findViewById(R.id.txtNombre);
            Email=(TextView) itemView.findViewById(R.id.codigo);
        }
        public void setData(incubadora incubadora) {
            Name.setText(incubadora.getName());
            Email.setText(incubadora.getCode());
        }
    }
}
