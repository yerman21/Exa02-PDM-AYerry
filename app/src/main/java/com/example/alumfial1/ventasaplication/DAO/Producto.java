package com.example.alumfial1.ventasaplication.DAO;

import java.util.ArrayList;
import java.util.HashMap;

public class Producto {
    private ArrayList<HashMap<String,Object>> productos;
/*    private String idd;
    private String name;
    private String foto;
    private int stock;


    Producto(String name, String foto, int stock) {
        this.name = name;
        this.foto = foto;
        this.stock = stock;
    }
*/

    public Producto(){
        productos=new ArrayList<HashMap<String,Object>>();
    }
    public void addProducto(HashMap<String,Object> objeto){
        productos.add(objeto);
    }

    public ArrayList<HashMap<String,Object>> getListaProductos() {
        return productos;
    }

    /*
    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
*/
}