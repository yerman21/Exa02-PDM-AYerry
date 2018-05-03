package com.example.alumfial1.ventasaplication.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alumfial1.ventasaplication.Adaptador.Venta_Adapter;
import com.example.alumfial1.ventasaplication.BoletaFragment;
import com.example.alumfial1.ventasaplication.EnviarMensaje;
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
    private EnviarMensaje em;
    private RecyclerView rv_catalogo;
    private EditText et_cantidad;
    private DatabaseReference mDatabase;
    private Venta_Adapter adapter;
    private ArrayList<HashMap<String,Object>> lista;
    private ArrayList<HashMap<String,Object>> carrito;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //porque no?? getView()
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_venta, container, false);

        presenter=new VentaPresentator(this);
        lista= new ArrayList<HashMap<String,Object>>();
        carrito=new ArrayList<HashMap<String,Object>>();
        rv_catalogo = (RecyclerView)view.findViewById(R.id.rv_venta);


        rv_catalogo.setItemAnimator(new DefaultItemAnimator());
        //Si rv_catalogo seguro que el tamaño del RecyclerView no se cambiará
        // , puedes añadirlo lo siguiente para mejorar el desempeño:
        rv_catalogo.setHasFixedSize(true);
        //voy a usar un LinearLayoutManager. Ésta subclase LayoutManager por defecto
        // hará que tu RecyclerView parezca una ListView.
        rv_catalogo.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_venta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BoletaFragment boletaFragment=new BoletaFragment();
                Bundle paquete=new Bundle();
                paquete.putSerializable("carrito",carrito);
                boletaFragment.setArguments(paquete);

                em.pasarDatosEntreFragments(boletaFragment);
/*                Intent intent=new Intent(getContext(),BoletaFragment.class);
                intent
                 Snackbar.make(view, "Vista previa de la venta", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
*/
            }
        });

        mDatabase= FirebaseDatabase.getInstance().getReference().child("producto");

        adapter=new Venta_Adapter(lista);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idd=lista.get(rv_catalogo.getChildAdapterPosition(view)).get("idd").toString();
                String precio=lista.get(rv_catalogo.getChildAdapterPosition(view)).get("precio").toString();
                String nombre=lista.get(rv_catalogo.getChildAdapterPosition(view)).get("nombre").toString();
                CrearDialog(view,idd,precio,nombre);
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

                elemento.put("idd",dataSnapshot.getKey());
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

    public void CrearDialog(final View view, final String idd, final String precio,final String nombre){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mView=inflater.inflate(R.layout.dialog_cantidad, null);
        et_cantidad=(EditText)mView.findViewById(R.id.et_diag_cantidad);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        alertDialogBuilder.setView(mView)
                // Add action buttons
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        HashMap<String,Object> elemento_agregar=new HashMap<String,Object>();
                        elemento_agregar.put("idd",idd);
                        elemento_agregar.put("nombre",nombre);
                        elemento_agregar.put("precio",precio);
                        elemento_agregar.put("cantidad",et_cantidad.getText().toString());

                        if(carrito.add(elemento_agregar)){
                            Toast.makeText(getActivity()," Se Agrego "+elemento_agregar.get("cantidad")+" "+nombre
                                    +" al carrito",Toast.LENGTH_LONG).show();
                           view.findViewById(R.id.cardview_01).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        }
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

    //Se llama cuando un fragmento se asocia por primera vez a su contexto
    @Override
    public void onAttach(Context context) {
         super.onAttach(context);
         em=(EnviarMensaje)context;
    }
}
