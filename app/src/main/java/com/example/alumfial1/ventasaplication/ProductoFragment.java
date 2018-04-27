package com.example.alumfial1.ventasaplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alumfial1.ventasaplication.DAO.Producto;

import java.util.HashMap;
import java.util.Map;

public class ProductoFragment extends Fragment {
    private Producto producto;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //porque no?? getView()
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_producto, container, false);
/*
        RecyclerView rv = (RecyclerView)view.findViewById(R.id.rv);
        //Si estás seguro que el tamaño del RecyclerView no se cambiará
        // , puedes añadirlo lo siguiente para mejorar el desempeño:
        rv.setHasFixedSize(true);
        //voy a usar un LinearLayoutManager. Ésta subclase LayoutManager por defecto
        // hará que tu RecyclerView parezca una ListView.
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
*/
        producto=new Producto();
        return view;
    }
    public void IniciarData(){
        producto=new Producto();
        HashMap<String,Object> producto_map=new HashMap();
        producto_map.put("nombre", "yogurt Yaya de Coco");
        producto_map.put("imagen",R.drawable.yogurt_yaya_coco);
        producto_map.put("stock", "25");
        producto.addProducto(producto_map);

        producto_map=new HashMap();
        producto_map.put("nombre", "yogurt Yaya de Fresa");
        producto_map.put("imagen",R.drawable.yogurt_yaya_fresa);
        producto_map.put("stock", "25");
        producto.addProducto(producto_map);
    }


}
