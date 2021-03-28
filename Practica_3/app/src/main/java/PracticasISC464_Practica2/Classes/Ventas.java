package PracticasISC464_Practica2.Classes;
import PracticasISC464_Practica2.Classes.*;
import PracticasISC464_Practica2.util.*;
import java.util.Date;
import java.util.Objects;
import java.time.LocalDate;
import org.h2.tools.Server;
import java.sql.*;
import java.util.*;

public class Ventas{

    private String id_Venta;
    private LocalDate fecha_Venta;
    private String nombreCiente;
    private ArrayList<Producto> misProductos;
    private CarroCompra misVentas;
    private static Ventas miVenta;
    private double subTotal;

 
    private int idgenerator=0;



    public Ventas() {
    }


    public Ventas(String nombreCiente, CarroCompra carroCompra, double subTotal) {

        
        // if(Almacen.getInstance().getVentaByID("BSC"+idgenerator) == true){
        // while(Almacen.getInstance().getVentaByID("BSC"+idgenerator) == true){
        //     System.out.println("Hola");
        //     System.out.println(idgenerator);
        //      idgenerator++;
        //      this.id_Venta = "BSC"+idgenerator;
        //      if(Almacen.getInstance().getVentaByID("BSC"+idgenerator) == false){
        //          break;}
        // }}else{ 
        //     this.id_Venta = "BSC"+idgenerator;
        // }


       
        this.fecha_Venta = java.time.LocalDate.now();
        this.nombreCiente = nombreCiente;
        this.misVentas= carroCompra;
        this.subTotal = subTotal;
        idgenerator++;
    }


    public static Ventas getInstance() { 
        if(miVenta==null) { 
            miVenta= new Ventas();
        }
      return miVenta;
  }
    



    public String getId_Venta() {
        return this.id_Venta;
    }

    public void setId_Venta(String id_Venta) {
        this.id_Venta = id_Venta;
    }

    public LocalDate getFecha_Venta() {
        return this.fecha_Venta;
    }

    public void setFecha_Venta(LocalDate fecha_Venta) {
        this.fecha_Venta = fecha_Venta;
    }


    public CarroCompra getMisVentas() {
        return this.misVentas;
    }

    public void setMisVentas(CarroCompra misVentas) {
        this.misVentas = misVentas;
    }

    public int getIdgenerator() {
        return this.idgenerator;
    }

    public void setIdgenerator(int idgenerator) {
        this.idgenerator = idgenerator;
    }


    public String getNombreCiente() {
        return this.nombreCiente;
    }

    public void setNombreCiente(String nombreCiente) {
        this.nombreCiente = nombreCiente;
    }

    public ArrayList<Producto> getMisProductos() {
        return this.misProductos;
    }

    public void setMisProductos(ArrayList<Producto> misProductos) {
        this.misProductos = misProductos;
    }

    public double getSubTotal() {
        return this.subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }


   




}