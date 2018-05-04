package com.example.alumfial1.ventasaplication;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alumfial1.ventasaplication.interfaces.VentaInterface;
import com.example.alumfial1.ventasaplication.presentador.VentaPresentator;
import com.example.alumfial1.ventasaplication.view.VentaFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class BoletaFragment extends Fragment implements View.OnClickListener,VentaInterface.View {
    private ArrayList<HashMap<String,Object>> carrito;
    private TextView tv_boleta_cantidad,tv_boleta_producto,tv_boleta_precio,tv_boleta_subtotal,tv_boleta_total;
    private FloatingActionButton fab_add_venta;
    private VentaInterface.Presenter presenter;
    private EnviarMensaje em;
    private Double num_total;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_boleta, container, false);

        presenter=new VentaPresentator(this);

        tv_boleta_cantidad=(TextView)view.findViewById(R.id.tv_boleta_cantidad);
        tv_boleta_producto=(TextView)view.findViewById(R.id.tv_boleta_producto);
        tv_boleta_precio=(TextView)view.findViewById(R.id.tv_boleta_precio);
        tv_boleta_subtotal=(TextView)view.findViewById(R.id.tv_boleta_subtotal);
        tv_boleta_total=(TextView)view.findViewById(R.id.tv_boleta_total);
        fab_add_venta=(FloatingActionButton)view.findViewById(R.id.fab_add_venta);

        carrito = new ArrayList<HashMap<String,Object>>();
        num_total=new Double(0);

        fab_add_venta.setOnClickListener(this);


        Bundle b = this.getArguments();
        if(b.getSerializable("carrito") != null) {
            carrito = (ArrayList<HashMap<String, Object>>) b.getSerializable("carrito");
            String cadena_cantidad="";
            String cadena_producto="";
            String cadena_precio="";
            String cadena_subtotal="";
            num_total=new Double(0);
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
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_add_venta:
                presenter.addVenta("Juanito Perez",carrito,"Vendedor1",num_total.toString());
            break;
        }
    }

    @Override
    public void notificar(int codigo) {
        if (codigo>0){
            Toast.makeText(getContext(),"Realizado con Satisfacion", Toast.LENGTH_LONG)
                    .show();
            em.pasarDatosEntreFragments(new VentaFragment());
        }
    }

    //Se llama cuando un fragmento se asocia por primera vez a su contexto
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        em=(EnviarMensaje)context;
    }
}
