package com.example.alumfial1.ventasaplication.presentador;

import com.example.alumfial1.ventasaplication.interfaces.ProductoInterface;
import com.example.alumfial1.ventasaplication.model.ProductoModel;
import com.example.alumfial1.ventasaplication.view.ProductoFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ProductoPresentator implements ProductoInterface.Presenter{
    private ProductoInterface.View view;
    private ProductoInterface.Model model;

    public ProductoPresentator(ProductoFragment view){
        this.view=view;
        model=new ProductoModel(this);
    }

    @Override
    public void notificar(int codigo) {
            view.notificar(codigo);
    }

    @Override
    public DatabaseReference getDatabaseReference(){
        return model.getDatabaseReference();
    }

    @Override
    public ArrayList<HashMap<String,Object>> getLista() {
        return model.getLista();
    }

    @Override
    public void modificarStock(String idd, int cantidad) {
        model.modificarStock(idd,cantidad);
    }

    @Override
    public void addProducto(String idd, String nombre, Double precio, int Stock) {
        model.addProducto(idd,nombre,precio,Stock);
    }
}