package PracticasISC464_Practica2.Classes;

import PracticasISC464_Practica2.Classes.*;
import PracticasISC464_Practica2.util.*;
import java.util.Objects;
import java.util.*;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;
import org.h2.tools.Server;
import java.sql.*;

public class Almacen{

    private ArrayList<Producto> productosAlmacen;
    private String idAlmacen; 
    private ArrayList<Usuario> usuariosAlmacen;
    private  CarroCompra comprasAlmacen;
    private ArrayList<Ventas> misVentas;
    private boolean adm_user = false;
    private boolean normal_user = false;
    private static Almacen miAlmacen;



    public Almacen() {
        
        productosAlmacen = new ArrayList<Producto>();
        usuariosAlmacen = new ArrayList<Usuario>();
        misVentas = new ArrayList<Ventas>();

    //     Usuario admin = new Usuario("admin", "Brayan_Admin","admin");
    //     usuariosAlmacen.add(admin);

    //     Producto p1 = new Producto("001","iPhone 7 Plus", 1500.00,0);
    //     Producto p2 = new Producto("002","iPhone 8 Plus", 3000.00,0);
    //     productosAlmacen.add(p1);
    //     productosAlmacen.add(p2);
     }


    public static Almacen getInstance() { 
        if(miAlmacen==null) { 
            miAlmacen= new Almacen();
        }
      return miAlmacen;
  }



    public ArrayList<Producto> getProductosAlmacen() {
        return this.productosAlmacen;
    }

    public void setProductosAlmacen(ArrayList<Producto> productosAlmacen) {
        this.productosAlmacen = productosAlmacen;
    }

    public String getIdAlmacen() {
        return this.idAlmacen;
    }

    public void setIdAlmacen(String idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public ArrayList<Usuario> getUsuariosAlmacen() {
        return this.usuariosAlmacen;
    }

    public void setUsuariosAlmacen(ArrayList<Usuario> usuariosAlmacen) {
        this.usuariosAlmacen = usuariosAlmacen;
    }

    public CarroCompra getComprasAlmacen() {
        return this.comprasAlmacen;
    }

    public void setComprasAlmacen(CarroCompra comprasAlmacen) {
        this.comprasAlmacen = comprasAlmacen;
    }

    public ArrayList<Ventas> getMisVentas() {
        return this.misVentas;
    }

    public void setMisVentas(ArrayList<Ventas> misVentas) {
        this.misVentas = misVentas;
    }

    public boolean isAdm_user() {
        return this.adm_user;
    }

    public boolean getAdm_user() {
        return this.adm_user;
    }

    public void setAdm_user(boolean adm_user) {
        this.adm_user = adm_user;
    }

    public boolean isNormal_user() {
        return this.normal_user;
    }

    public boolean getNormal_user() {
        return this.normal_user;
    }

    public void setNormal_user(boolean normal_user) {
        this.normal_user = normal_user;
    }



    public void addProducto(Producto auxProducto){
        getProductosAlmacen().add(auxProducto);
    }

    public void eliminarProducto(Producto aux){
        getProductosAlmacen().remove(aux);
    }

   

    public void editarProducto(Producto aux){

        Producto auxProducto = buscarProductoByID(aux.getId_Product());
        if(auxProducto == null){
            addProducto(aux);
           
        }
        else {
        auxProducto.setName_Product(aux.getName_Product());
        auxProducto.setPrice_Product(aux.getPrice_Product());
    } 
}




    public Producto buscarProductoByID(String auxId){
        boolean encontrado = false;
        Producto auxProducto = null;
        int i=0;

        while(i<getProductosAlmacen().size() && !encontrado){
            if(getProductosAlmacen().get(i).getId_Product().equalsIgnoreCase(auxId)){
                auxProducto = getProductosAlmacen().get(i);
                encontrado = true;
                return auxProducto;
            }
            i++;
            
        }
        return auxProducto;
    }

    public Usuario getUserByName(String username){
        boolean encontrado = false;
        Usuario auxUsuario = null;
        int i=0;

        while(i<usuariosAlmacen.size() && !encontrado){
            if(usuariosAlmacen.get(i).getUser().equalsIgnoreCase(username)){
                auxUsuario = usuariosAlmacen.get(i);
                encontrado = true;
            }
            i++;
            return auxUsuario;
        }
        return auxUsuario;
    }

    public Usuario login_User(String username, String password){

        Usuario auxUsuario = getUserByUserName(username);
        if(auxUsuario == null){
            throw new RuntimeException("El usuario: "+username+"no existe.");
        } else if(auxUsuario.getUser().equals("admin") && auxUsuario.getPassword().equals("admin")){
            adm_user = true;
            normal_user = false;
           
            return auxUsuario;
        } else if(auxUsuario.getUser().equals(username) && auxUsuario.getPassword().equals(password)){
            adm_user = false;
            normal_user = true;
            return auxUsuario;
        } else throw new RuntimeException("Usuario o password incorrecto!");

       
    }

    public boolean getCurrentUser(){
        return adm_user;
    }

    public void logout(){
        adm_user = false;
        normal_user = false;
    }



    public ArrayList<Producto> getListaProductos(){
        ArrayList<Producto> auxProd = new ArrayList<Producto>();
        Connection con = null;
        try {
            String query = "SELECT * FROM PRODUCTOS;";
            con = DataBaseServices.getInstance().getConexion();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Producto producto = new Producto();
                producto.setId_Product(rs.getString("ID_PRODUCT"));
                producto.setName_Product(rs.getString("NAME_PRODUCT"));
                producto.setPrice_Product(rs.getDouble("PRICE_PRODUCT"));
                auxProd.add(producto);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return auxProd;
    }


    public boolean insertarProductoDB(Producto producto){


        Producto auxProducto = getProductoByIDDB(producto.getId_Product());
        if(auxProducto == null){
            
        boolean ok =false;

        Connection con = null;
        try {
            String query = "INSERT INTO PRODUCTOS(ID_PRODUCT,NAME_PRODUCT, PRICE_PRODUCT) VALUES(?,?, ?)";
            con = DataBaseServices.getInstance().getConexion();
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, producto.getId_Product());
            ps.setString(2, producto.getName_Product());
            ps.setDouble(3, producto.getPrice_Product());
            int check = ps.executeUpdate();
            ok = check > 0 ;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return ok;
           
        }
        else {
                boolean ok = false;
                Connection con = null;
                try {
                    String query = "UPDATE PRODUCTOS SET NAME_PRODUCT = ?, PRICE_PRODUCT = ? WHERE ID_PRODUCT = ?";
                    con = DataBaseServices.getInstance().getConexion();
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, producto.getName_Product());
                    ps.setDouble(2, producto.getPrice_Product());
                    ps.setString(3, producto.getId_Product())
                    ;
        
                    int check = ps.executeUpdate();
                    ok = check > 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return ok;
            
    } 

    }


    public Producto getProductoByIDDB(String id){
        Connection con = null;
        Producto producto = null;
        try {
            String query = "SELECT * FROM PRODUCTOS WHERE ID_PRODUCT = ?";
            con = DataBaseServices.getInstance().getConexion();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                producto = new Producto();
                producto.setId_Product(rs.getString("ID_PRODUCT"));
                producto.setName_Product(rs.getString("NAME_PRODUCT"));
                producto.setPrice_Product(rs.getDouble("PRICE_PRODUCT"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return producto;
    }


    public boolean eliminarProductoDB(Producto producto){
        boolean ok = false;
        Connection con = null;
        try {
            String query = "DELETE FROM PRODUCTOS WHERE ID_PRODUCT = ?";
            con = DataBaseServices.getInstance().getConexion();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, producto.getId_Product());

            int check = ps.executeUpdate();
            ok = check > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }



    public Usuario getUserByUserName(String user_name){
        Usuario usuario = null;
        Connection con = null;
        try {
            String query = "SELECT * FROM USUARIO WHERE USER = ?";
            con = DataBaseServices.getInstance().getConexion();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user_name);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                usuario = new Usuario(rs.getString("user"),rs.getString("full_name"),rs.getString("password"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return usuario;
    }


    public ArrayList<Usuario> getListaUsuariosDB() {
        ArrayList<Usuario> Usuarios = new ArrayList<Usuario>();
        Connection con = null;
        try {
            String query = "SELECT * FROM USUARIO;";
            con = DataBaseServices.getInstance().getConexion();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Usuario usuario = new Usuario(rs.getString("USER"),rs.getString("FULL_NAME"),rs.getString("PASSWORD"));
                Usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return Usuarios;
    }


    public boolean procesarCompraDB(Ventas venta){
        boolean ok = false;
        Connection con = null;
        try {
            String query = "INSERT INTO VENTAS(ID_VENTA, NOMBRE_CLIENTE, SUBTOTAL, TOTAL) VALUES(?, ?, ?, ?)";
            con = DataBaseServices.getInstance().getConexion();
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,venta.getId_Venta());
            ps.setString(2, venta.getNombreCiente());
            ps.setDouble(3, venta.getSubTotal());
            ps.setDouble(4, venta.getSubTotal() * (1.18));
            int check = ps.executeUpdate();
            ok = check > 0;
            int idVenta = 0;
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                idVenta = generatedKeys.getInt(1);
            }

       
            String query2 = "INSERT INTO PRODVENTA(ID_PRODUCTO, ID_VENTA, CANTIDAD) VALUES(?, ?, ?)";
            PreparedStatement ps1 = con.prepareStatement(query2);
            for(int i=0; i<venta.getMisVentas().getMisProductos().size(); i++) {
                ps1.setString(1, venta.getMisVentas().getMisProductos().get(i).getId_Product());
                ps1.setInt(2, idVenta);
                ps1.setInt(3, venta.getMisVentas().buscarProductoByID(venta.getMisVentas().getMisProductos().get(i).getId_Product()).getQty_Product());
                ps1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok;
    }


    public boolean getVentaByID(String user_name){
        boolean encontrado = false;
        Connection con = null;
        try {
            String query = "SELECT * FROM Ventas WHERE ID_VENTA = ?";
            con = DataBaseServices.getInstance().getConexion();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user_name);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                encontrado = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return encontrado;
    }

    public ArrayList<Ventas> getMisVentasDB(){
        ArrayList<Ventas> ventas = new ArrayList<Ventas>();
        Connection con = null;
        try {
            String queryVta = "SELECT * FROM VENTAS;";
            con = DataBaseServices.getInstance().getConexion();
            PreparedStatement ps = con.prepareStatement(queryVta);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Ventas venta = new Ventas();
                venta.setId_Venta(Integer.toString(rs.getInt("ID_VENTA")));
                venta.setFecha_Venta(null);
                venta.setNombreCiente(rs.getString("NOMBRE_CLIENTE"));
                ArrayList<Producto> listaProd = new ArrayList<Producto>();
                venta.setMisProductos(listaProd);
                ventas.add(venta);
            }

            String queryProd = "SELECT * FROM PRODVENTA WHERE ID_VENTA = ?";
            for(int i = 0; i < ventas.size(); i++) {
                PreparedStatement ps1 = con.prepareStatement(queryProd);
                ps1.setInt(1, Integer.parseInt(ventas.get(i).getId_Venta()));
                ResultSet rs1 = ps1.executeQuery();
                while(rs1.next()){
                    String idProd = rs1.getString("ID_PRODUCTO");
                    int cant = rs1.getInt("CANTIDAD");
                    Producto tmp = getProductoByIDDB(idProd);
                    tmp.setQty_Product(cant);
                    ventas.get(i).getMisProductos().add(tmp);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return ventas;
    }

    
}