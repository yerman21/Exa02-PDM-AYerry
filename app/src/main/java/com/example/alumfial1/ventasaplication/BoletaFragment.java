package com.example.alumfial1.ventasaplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class BoletaFragment extends Fragment {

    private ArrayList<HashMap<String,Object>> carrito;
    public BoletaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        carrito = new ArrayList<HashMap<String,Object>>();
        Bundle b = this.getArguments();
        if(b.getSerializable("carrito") != null) {
            carrito = (ArrayList<HashMap<String, Object>>) b.getSerializable("carrito");
        }
        Toast.makeText(getContext(),"nose "+carrito.get(0).get("nombre").toString(),Toast.LENGTH_LONG).show();

        return inflater.inflate(R.layout.fragment_boleta, container, false);
    }


}
