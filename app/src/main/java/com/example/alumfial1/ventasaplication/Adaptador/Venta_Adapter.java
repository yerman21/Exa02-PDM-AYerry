package com.example.alumfial1.ventasaplication.Adaptador;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alumfial1.ventasaplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Venta_Adapter
        extends RecyclerView.Adapter<Venta_Adapter.CatalogoViewHolder>
        implements View.OnClickListener{
    private ArrayList<HashMap<String,Object>> listaCatalogo;
    private View.OnClickListener listener;

    public Venta_Adapter(ArrayList<HashMap<String,Object>> listaCatalogo){
        this.listaCatalogo=listaCatalogo;
    }
    //Éste método es llamado cuando el ViewHolder necesita ser inicializado.
    // Especificamos el layout que cada elemento de RecyclerView debería usar.
    // Ésto se hace al inflar el layout usando LayoutInflater, pasando el resultado al constructor del ViewHolder.
    @Override
    public CatalogoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venta_catalogo, parent, false);
        CatalogoViewHolder pvh = new CatalogoViewHolder(v);

        v.setOnClickListener(this);
        return pvh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    //para especificar el contenido de cada elemento del RecyclerView.
    @Override
    public void onBindViewHolder(CatalogoViewHolder holder, int position) {
        holder.nombre.setText(listaCatalogo.get(position).get("nombre").toString());
        holder.stock.setText("Cantidad: "+listaCatalogo.get(position).get("stock").toString());
        holder.img.setImageResource(R.drawable.yogurt_yaya_coco);
        holder.precio.setText("Precio: s/"+listaCatalogo.get(position).get("precio").toString());



        //holder.nombre.setText(listaProductos (position).get("nombre").toString());
    }

    @Override
    public int getItemCount() {
        return listaCatalogo.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public static class CatalogoViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView nombre,stock,precio;
        ImageView img;
        ImageButton imgBtn;

        CatalogoViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardview_01);
            imgBtn=(ImageButton)itemView.findViewById(R.id.imgbtn_agregar_card01);

            nombre = (TextView)itemView.findViewById(R.id.et_titulo_card01);
            stock = (TextView)itemView.findViewById(R.id.et_stock_card01);
            precio=(TextView)itemView.findViewById(R.id.et_precio_card01);
            img = (ImageView)itemView.findViewById(R.id.imgv_card01);
        }
    }
}
