package com.example.alumfial1.ventasaplication.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alumfial1.ventasaplication.Adaptador.Venta_Adapter;
import com.example.alumfial1.ventasaplication.R;
import com.example.alumfial1.ventasaplication.interfaces.VentaInterface;
import com.example.alumfial1.ventasaplication.presentador.VentaPresentator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class VentaFragment extends Fragment implements VentaInterface.View{
    private VentaInterface.Presenter presenter;
    private RecyclerView rv_catalogo;
    private DatabaseReference mDatabase;
    private Venta_Adapter adapter;
    private ArrayList<HashMap<String,Object>> lista;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //porque no?? getView()
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_venta, container, false);

        presenter=new VentaPresentator(this);
        lista= new ArrayList<HashMap<String,Object>>();
        rv_catalogo = (RecyclerView)view.findViewById(R.id.rv_venta);

        rv_catalogo.setItemAnimator(new DefaultItemAnimator());
        //Si rv_catalogo seguro que el tamaño del RecyclerView no se cambiará
        // , puedes añadirlo lo siguiente para mejorar el desempeño:
        rv_catalogo.setHasFixedSize(true);
        //voy a usar un LinearLayoutManager. Ésta subclase LayoutManager por defecto
        // hará que tu RecyclerView parezca una ListView.
        rv_catalogo.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabase= FirebaseDatabase.getInstance().getReference().child("producto");

        adapter=new Venta_Adapter(lista);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearDialog(view);
            }
        });

        getDataFirebase();
        return view;
    }

    public void getDataFirebase(){
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap<String,Object> elemento=new HashMap<>();

                elemento.put("nombre",dataSnapshot.child("nombre").getValue(String.class));
                elemento.put("stock",dataSnapshot.child("stock").getValue(int.class));
                elemento.put("precio",String.format("%.2f",1.4*dataSnapshot.child("precio").getValue(Double.class)));

                lista.add(elemento);

                rv_catalogo.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
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



    @Override
    public void notificar(int codigo) {
        Toast.makeText(getContext(),"Realizado con Satisfacion", Toast.LENGTH_LONG)
                .show();
    }

    public void CrearDialog(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        alertDialogBuilder.setView(inflater.inflate(R.layout.dialog_cantidad, null))
                // Add action buttons
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(),"Holaaaa",Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
