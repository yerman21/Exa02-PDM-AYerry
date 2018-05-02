package com.example.alumfial1.ventasaplication.presentador;

import com.example.alumfial1.ventasaplication.interfaces.VentaInterface;
import com.example.alumfial1.ventasaplication.model.VentaModel;
import com.example.alumfial1.ventasaplication.view.VentaFragment;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

public class VentaPresentator implements VentaInterface.Presenter {
    private VentaInterface.View view;
    private VentaInterface.Model model;

    public VentaPresentator(VentaFragment view){
        this.view=view;
        model=new VentaModel(this);
    }

    @Override
    public void notificar(int codigo) {
        view.notificar(codigo);
    }

    @Override
    public void addVenta(String cliente, ArrayList<HashMap<String, HashMap<String, Object>>> productos, String vendedor) {
        model.addVenta(cliente, productos, vendedor);
    }
}
