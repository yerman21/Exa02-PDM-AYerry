package com.example.alumfial1.ventasaplication.view;


import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alumfial1.ventasaplication.Adaptador.RVAdapter;

import com.example.alumfial1.ventasaplication.R;
import com.example.alumfial1.ventasaplication.interfaces.ProductoInterface;
import com.example.alumfial1.ventasaplication.presentador.ProductoPresentator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductoFragment extends Fragment implements ProductoInterface.View{
    private ProductoInterface.Presenter presenter;
    private RecyclerView rv_producto;
    private DatabaseReference mDatabase;
    private RVAdapter adapter;

    private ArrayList<HashMap<String,Object>> lista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //porque no?? getView()
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_producto, container, false);

        presenter=new ProductoPresentator(this);
        lista= new ArrayList<HashMap<String,Object>>();
        rv_producto = (RecyclerView)view.findViewById(R.id.rv);

        rv_producto.setItemAnimator(new DefaultItemAnimator());
        //Si estás seguro que el tamaño del RecyclerView no se cambiará
        // , puedes añadirlo lo siguiente para mejorar el desempeño:
        rv_producto.setHasFixedSize(true);
        //voy a usar un LinearLayoutManager. Ésta subclase LayoutManager por defecto
        // hará que tu RecyclerView parezca una ListView.
        rv_producto.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_producto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Vista add producto", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDatabase= FirebaseDatabase.getInstance().getReference().child("producto");
        adapter=new RVAdapter(lista);

        getDataFirebase();
        return view;
    }

    @Override
    public void notificar(int codigo) {
        Toast.makeText(getContext(),"Realizado con Satisfacion", Toast.LENGTH_LONG)
                .show();
    }

    void getDataFirebase(){
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap<String,Object> elemento=new HashMap<>();
                elemento.put("nombre",dataSnapshot.child("nombre").getValue(String.class));
                elemento.put("stock",dataSnapshot.child("stock").getValue(int.class));

                lista.add(elemento);
                rv_producto.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                HashMap<String,Object> elemento=new HashMap<>();
                elemento.put("nombre",dataSnapshot.child("nombre").getValue(String.class));
                elemento.put("stock",dataSnapshot.child("stock").getValue(int.class));

                lista.add(elemento);
                rv_producto.setAdapter(adapter);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
