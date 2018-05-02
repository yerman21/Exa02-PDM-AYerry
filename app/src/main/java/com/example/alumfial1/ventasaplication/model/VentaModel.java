package com.example.alumfial1.ventasaplication.model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.alumfial1.ventasaplication.interfaces.VentaInterface;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class VentaModel implements VentaInterface.Model {
    private VentaInterface.Presenter presenter;
    final private DatabaseReference
            productoReferences= FirebaseDatabase.getInstance().getReference().child("producto")
            ,ventaReferences= FirebaseDatabase.getInstance().getReference().child("venta");
    private ArrayList<HashMap<String,Object>> lista;

    public VentaModel(VentaInterface.Presenter presenter){
        this.presenter=presenter;
        lista=new ArrayList< HashMap<String,Object>>();
    }

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

    @Override
    public void addVenta(String cliente, ArrayList<HashMap<String,HashMap<String,Object>>> productos,String vendedor) {
        HashMap<String,Object> venta=new HashMap();
        HashMap<String,Object> productosVendidos=new HashMap();
        Double total=0.0;

        for (HashMap<String, HashMap<String, Object>> hash:productos) {
               String clave=hash.keySet().toString();
               Double subTotal= ((int)hash.get(clave).get("cantidad"))* ((Double)hash.get(clave).get("precio"));
               total=total+subTotal;

               productosVendidos.put(clave,(int)hash.get(clave).get("cantidad"));
        }

        venta.put("cliente",cliente);
        venta.put("igv",total*0.18);
        venta.put("productos",productos);
        venta.put("total",total);
        venta.put("vendedor",vendedor);
        productoReferences.push().setValue(venta);

        for (HashMap.Entry<String, Object> entry : productosVendidos.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            modificarStock(key,(int)value);
        }

        presenter.notificar(1);
    }
}
