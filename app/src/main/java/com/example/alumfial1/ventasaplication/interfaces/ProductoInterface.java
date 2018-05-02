package com.example.alumfial1.ventasaplication.interfaces;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public interface ProductoInterface {
    interface View{
        void notificar(int codigo);
    }
    interface Presenter{
        void notificar(int codigo);
        ArrayList<HashMap<String,Object>> getLista();
        void modificarStock(String idd,int cantidad);
        void addProducto(String idd,String nombre,Double precio,int Stock);
        DatabaseReference getDatabaseReference();
    }
    interface Model{
        ArrayList<HashMap<String,Object>> getLista();
        void modificarStock(String idd,int cantidad);
        void addProducto(String idd,String nombre,Double precio,int Stock);
        DatabaseReference getDatabaseReference();
    }
}
