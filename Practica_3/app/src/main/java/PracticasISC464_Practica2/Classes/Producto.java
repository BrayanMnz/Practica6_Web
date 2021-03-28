package PracticasISC464_Practica2.Classes;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.*;

public class Producto{
    
    public String id_Product;
    public String name_Product;
    public Double price_Product;
    public int qty_Product;


    public Producto(String id_Product, String name_Product, Double price_Product, int qty_Product) {
        this.id_Product = id_Product;
        this.name_Product = name_Product;
        this.price_Product = price_Product;
        this.qty_Product = qty_Product;
    }



    public Producto() {
    }




    public String getId_Product() {
        return this.id_Product;
    }

    public void setId_Product(String id_Product) {
        this.id_Product = id_Product;
    }

    public String getName_Product() {
        return this.name_Product;
    }

    public void setName_Product(String name_Product) {
        this.name_Product = name_Product;
    }

    public Double getPrice_Product() {
        return this.price_Product;
    }

    public void setPrice_Product(Double price_Product) {
        this.price_Product = price_Product;
    }

    public int getQty_Product() {
        return this.qty_Product;
    }

    public void setQty_Product(int qty_Product) {
        this.qty_Product =  qty_Product;
    }

    public void cantidadProducto(int qty_Product){
        setQty_Product(getQty_Product() + qty_Product);  
    }



}