package com.example.alumfial1.ventasaplication.Adaptador;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alumfial1.ventasaplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ProductoViewHolder>{
    private ArrayList<HashMap<String,Object>> listaProductos;

    public RVAdapter(ArrayList<HashMap<String,Object>> listaProductos){
        this.listaProductos=listaProductos;
    }
    //Éste método es llamado cuando el ViewHolder necesita ser inicializado.
    // Especificamos el layout que cada elemento de RecyclerView debería usar.
    // Ésto se hace al inflar el layout usando LayoutInflater, pasando el resultado al constructor del ViewHolder.
    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ProductoViewHolder pvh = new ProductoViewHolder(v);
        return pvh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    //para especificar el contenido de cada elemento del RecyclerView.
    @Override
    public void onBindViewHolder(ProductoViewHolder holder, int position) {
            holder.nombre.setText(listaProductos.get(position).get("nombre").toString());
            holder.stock.setText(listaProductos.get(position).get("stock").toString());
            holder.img.setImageResource(R.drawable.yogurt_yaya_fresa);

        //holder.nombre.setText(listaProductos (position).get("nombre").toString());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView nombre;
        TextView stock;
        ImageView img;

        ProductoViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardview_01);
            nombre = (TextView)itemView.findViewById(R.id.et_titulo_card01);
            stock = (TextView)itemView.findViewById(R.id.et_stock_card01);
            img = (ImageView)itemView.findViewById(R.id.imgv_card01);
        }
    }
}