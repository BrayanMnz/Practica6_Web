package PracticasISC464_Practica2.Classes;
import java.util.Objects;
import java.util.ArrayList;

public class CarroCompra {

    private ArrayList <Producto> misProductos;
    private static CarroCompra miCarro;
    private String id_Compra;
    private int cantidadArticulosCarrito=0;

    


    public CarroCompra() {
    
    }

    public CarroCompra(String id_Compra, ArrayList<Producto> misProductos) {
        this.misProductos = misProductos;
        this.id_Compra = id_Compra;
    }

    public static CarroCompra getInstance() { 
        if(miCarro==null) { 
            miCarro= new CarroCompra();
        }
      return miCarro;
  }
    

    public ArrayList<Producto> getMisProductos() {
        return this.misProductos;
    }

    public void addProducto(Producto aux){
        if(buscarProductoByID(aux.getId_Product()) == null){
        getMisProductos().add(aux);
        }else {
            boolean encontrado = false;
            Producto auxProducto = null;
            int i=0;
    
            while(i<getMisProductos().size() && !encontrado){
                if(getMisProductos().get(i).getId_Product().equalsIgnoreCase(aux.getId_Product())){
                    auxProducto = getMisProductos().get(i);
                    encontrado = true;
                    int qty = auxProducto.getQty_Product() + Almacen.getInstance().getProductoByIDDB(aux.getId_Product()).getQty_Product();
                    auxProducto.cantidadProducto(qty);
                }
                i++;
                
            }
        }
    }

    public Producto buscarProductoByID(String auxId){
        boolean encontrado = false;
        Producto auxProducto = null;
        int i=0;

        while(i<getMisProductos().size() && !encontrado){
            if(getMisProductos().get(i).getId_Product().equalsIgnoreCase(auxId)){
                auxProducto = getMisProductos().get(i);
                encontrado = true;
                return auxProducto;
            }
            i++;
            
        }
        return auxProducto;
    }


    public void setMisProductos(ArrayList<Producto> misProductos) {
        this.misProductos = misProductos;
    }

    public String getId_Compra() {
        return this.id_Compra;
    }

    public void setId_Compra(String id_Compra) {
        this.id_Compra = id_Compra;
    }

    public int cantidadArticulosCarrito(){
        int i=0;
        int qty =0;
        while(i<getMisProductos().size()){
            
            qty += getMisProductos().get(i).getQty_Product();
            // return qty;
            i++;
        }
        return qty;
    }



    public int getCantidadArticulosCarrito() {
        return this.cantidadArticulosCarrito;
    }

    public void setCantidadArticulosCarrito(int cantidadArticulosCarrito) {

        
        this.cantidadArticulosCarrito = cantidadArticulosCarrito + getCantidadArticulosCarrito();
    }

    public double totalCarrito(){
        int i =0;
        double total =0;
        while(i<getMisProductos().size()){
            total += getMisProductos().get(i).getPrice_Product() * getMisProductos().get(i).getQty_Product();
            i++;
            
        }

        return total;
    }

}