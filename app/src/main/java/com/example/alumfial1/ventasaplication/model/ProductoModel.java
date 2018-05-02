package com.example.alumfial1.ventasaplication.model;

import android.util.Log;

import com.example.alumfial1.ventasaplication.interfaces.ProductoInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductoModel implements ProductoInterface.Model{
    private ProductoInterface.Presenter presenter;
    private DatabaseReference productoReferences;
    private ArrayList<HashMap<String,Object>> lista;

    public ProductoModel(ProductoInterface.Presenter presenter){
        this.presenter=presenter;
        lista= new ArrayList<HashMap<String,Object>>();
        productoReferences= FirebaseDatabase.getInstance().getReference().child("producto");
    }
    @Override
    public DatabaseReference getDatabaseReference(){
        return productoReferences;
    }

    //para listar todos los productos....<<por mejorar>>
    @Override
    public ArrayList<HashMap<String,Object>> getLista() {
        Log.w("_datosAdapter_","hola_despues"+lista.size());
        return lista;
    }
//para modificar el stock de un producto
    @Override
    public void modificarStock(String idd, final int cantidad) {
        final DatabaseReference reference=productoReferences.child(idd);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reference.setValue("stock",((int)dataSnapshot.child("stock").getValue())-cantidad);
                presenter.notificar(1);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w( "error_product-m_update", databaseError.toException());
                presenter.notificar(0);
            }
        });
    }
//agregar un nuevo producto
    @Override
    public void addProducto(String idd, String nombre, Double precio, int stock) {
        HashMap<String,Object> producto=new HashMap();
        producto.put("nombre",nombre);
        producto.put("precio",precio);
        producto.put("stock",stock);
        productoReferences.child(idd).setValue(producto).isSuccessful();

        presenter.notificar(1);
    }
}
