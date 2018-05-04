package com.example.alumfial1.ventasaplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class BoletaFragment extends Fragment {
    private ArrayList<HashMap<String,Object>> carrito;
    private TextView tv_boleta_cantidad,tv_boleta_producto,tv_boleta_precio,tv_boleta_subtotal,tv_boleta_total;
    public BoletaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_boleta, container, false);

        carrito = new ArrayList<HashMap<String,Object>>();
        tv_boleta_cantidad=(TextView)view.findViewById(R.id.tv_boleta_cantidad);
        tv_boleta_producto=(TextView)view.findViewById(R.id.tv_boleta_producto);
        tv_boleta_precio=(TextView)view.findViewById(R.id.tv_boleta_precio);
        tv_boleta_subtotal=(TextView)view.findViewById(R.id.tv_boleta_subtotal);
        tv_boleta_total=(TextView)view.findViewById(R.id.tv_boleta_total);

        Bundle b = this.getArguments();
        if(b.getSerializable("carrito") != null) {
            carrito = (ArrayList<HashMap<String, Object>>) b.getSerializable("carrito");
            String cadena_cantidad="";
            String cadena_producto="";
            String cadena_precio="";
            String cadena_subtotal="";
            Double num_total=new Double(0);
            for (HashMap<String, Object> hm:carrito){
                Double num_subtotal=Double.parseDouble(hm.get("precio").toString())*Integer.valueOf(hm.get("cantidad").toString());
                num_subtotal=Double.parseDouble(String.format("%.2f",num_subtotal));

                cadena_cantidad=cadena_cantidad+hm.get("cantidad").toString()+ Html.fromHtml("<br />");
                cadena_producto=cadena_producto+hm.get("nombre").toString()+ Html.fromHtml("<br />");
                cadena_precio=cadena_precio+hm.get("precio").toString()+ Html.fromHtml("<br />");
                cadena_subtotal=cadena_subtotal+""+num_subtotal+ Html.fromHtml("<br />");

                num_total=num_total+num_subtotal;
            }
            tv_boleta_cantidad.setText(cadena_cantidad);
            tv_boleta_producto.setText(cadena_producto);
            tv_boleta_precio.setText(cadena_precio);
            tv_boleta_subtotal.setText(cadena_subtotal);
            tv_boleta_total.setText("Total: "+Double.toString(num_total));
        }
        Toast.makeText(getContext(),"nose "+carrito.get(0).get("nombre").toString(),Toast.LENGTH_LONG).show();


        return view;
    }


}
