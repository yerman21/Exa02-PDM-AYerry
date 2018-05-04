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
                reference.child("stock").setValue(Long.parseLong(dataSnapshot.child("stock").getValue().toString())-cantidad);
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
    public void addVenta(String cliente, ArrayList<HashMap<String,Object>> carrito_ventas,String vendedor,String total) {
        HashMap<String,Object> productosVendidos=new HashMap();
        HashMap<String,Object> productosCarrito=new HashMap();

        String primaryKey=ventaReferences.push().getKey();

        ventaReferences.child(primaryKey).child("cliente").setValue(cliente);
        ventaReferences.child(primaryKey).child("igv").setValue(Double.parseDouble(total)*0.18);
        ventaReferences.child(primaryKey).child("total").setValue(total);
        ventaReferences.child(primaryKey).child("vendedor").setValue(vendedor);

        for (HashMap<String, Object> hash:carrito_ventas) {
            String clave_producto=hash.get("idd").toString();

            hash.remove("idd");
            hash.remove("nombre");

            ventaReferences.child(primaryKey).child("producto").child(clave_producto).setValue(hash);
            modificarStock(clave_producto,Integer.parseInt(hash.get("cantidad").toString()));
        }
        presenter.notificar(1);
    }
}
