package com.example.alumfial1.ventasaplication.interfaces;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

public interface VentaInterface {
    interface View{
        void notificar(int codigo);
    }
    interface Presenter{
        void notificar(int codigo);
        void addVenta(String cliente, ArrayList<HashMap<String,HashMap<String,Object>>> productos,String vendedor);
    }
    interface Model{
        void addVenta(String cliente, ArrayList<HashMap<String,HashMap<String,Object>>> productos,String vendedor);
    }
}
