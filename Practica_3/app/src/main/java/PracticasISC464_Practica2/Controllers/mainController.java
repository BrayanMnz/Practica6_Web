package PracticasISC464_Practica2.Controllers;

import PracticasISC464_Practica2.util.*;
import PracticasISC464_Practica2.util.BaseControlador;
import PracticasISC464_Practica2.Classes.*;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import io.javalin.plugin.rendering.template.JavalinFreemarker;
import java.util.*;
import static io.javalin.apibuilder.ApiBuilder.*;


public class mainController extends BaseControlador {

    public mainController(Javalin app) {
        super(app);
        registrandoPlantillas();
    }

    /**
     * Registrando los sistemas de plantillas utilizados.
     */
    private void registrandoPlantillas(){
        //registrando los sistemas de plantilla.
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
    }

    CarroCompra auxCarrito;
    Almacen auxAlmacen = Almacen.getInstance();

    @Override
    public void aplicarRutas() {
        app.routes(() -> {

            before(ctx -> {
                auxCarrito = ctx.sessionAttribute("carrito");
                if(auxCarrito == null) {
                    ArrayList<Producto> auxProductos = new ArrayList<Producto>();
                    ctx.sessionAttribute("carrito", new CarroCompra("001", auxProductos));
                }
            });

            path("/Principal", () ->{

                get("/ListadoProductos", ctx -> {
                    // ArrayList<Producto> auxProductos = Almacen.getInstance().getProductosAlmacen();
                    ArrayList<Producto> auxProductos = Almacen.getInstance().getListaProductos();

                    Map<String, Object> modelo = new HashMap<>();
                    
                    int x = auxCarrito.getCantidadArticulosCarrito();
                    modelo.put("cantidad_Carrito", x);
                    modelo.put("titulo", "Listado Productos");
                    modelo.put("auxProductos", auxProductos);
                    modelo.put("usuario",Almacen.getInstance().getNormal_user());
                    modelo.put("admin",Almacen.getInstance().getCurrentUser());
                    modelo.put("usuario1", ctx.sessionAttribute("usuario"));

                    ctx.render("/publico/templates/Listado.html",modelo);
                });



                    get("/Admin/ListadoProductos", ctx -> {
                        ArrayList<Producto> auxProductos = Almacen.getInstance().getListaProductos();
                        
                        CarroCompra auxCarrito = ctx.sessionAttribute("auxCarrito");
                        Map<String, Object> modelo = new HashMap<>();
                        modelo.put("user",Almacen.getInstance().getCurrentUser());
                        modelo.put("titulo", "Listado Productos");
                        modelo.put("auxProductos", auxProductos);
                        ctx.render("/publico/templates/Listado_Admin.html",modelo);
                    });

                get("/CrearProductos", ctx -> {
                    CarroCompra auxCarrito = ctx.sessionAttribute("auxCarrito");
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("user",Almacen.getInstance().getCurrentUser());
                    modelo.put("titulo", "Crear o editar Productos");
                    modelo.put("idProducto","");
                    modelo.put("nombreProducto","");
                    modelo.put("precioProducto", 0);
                    ctx.render("/publico/templates/create_edit.html",modelo);
                });

               get("/CrearProductos/:id", ctx -> {
 
                    String aux = ctx.pathParam("id",String.class).get();
                    CarroCompra auxCarrito = ctx.sessionAttribute("auxCarrito");
                    Map<String, Object> modelo = new HashMap<>();
                    Producto auxProducto = Almacen.getInstance().getProductoByIDDB(aux);
                    modelo.put("user",Almacen.getInstance().getCurrentUser());
                    modelo.put("idProducto",auxProducto.getId_Product());
                    modelo.put("nombreProducto",auxProducto.getName_Product());
                    modelo.put("precioProducto",auxProducto.getPrice_Product());
                    modelo.put("titulo", "Crear o editar Productos");
                    ctx.render("/publico/templates/create_edit.html",modelo);
                });

               get("/EliminarProductos/:id", ctx -> {
 
                    String aux = ctx.pathParam("id",String.class).get();
                    CarroCompra auxCarrito = ctx.sessionAttribute("auxCarrito");
                    Map<String, Object> modelo = new HashMap<>();
                    Producto auxProducto = Almacen.getInstance().buscarProductoByID(aux);
                    modelo.put("user",Almacen.getInstance().getCurrentUser());
                    modelo.put("idProducto",auxProducto.getId_Product());
                    modelo.put("nombreProducto",auxProducto.getName_Product());
                    modelo.put("precioProducto",auxProducto.getPrice_Product());
                    modelo.put("titulo", "Eliminar Producto");

                    ctx.render("/publico/templates/eliminar.html",modelo);
                });

                post("/CrearProductos", ctx -> {

                    String id = ctx.formParam("id_Producto");
                    String nombre = ctx.formParam("nombre_Producto");
                    double precio = Double.parseDouble(ctx.formParam("precio_Producto"));
    
                    Producto auxProducto = new Producto(id,nombre,precio,0); 
                //    auxAlmacen.addProducto(auxProducto);
                    //  auxAlmacen.editarProducto(auxProducto);
                     auxAlmacen.insertarProductoDB(auxProducto);
                     
                    ctx.redirect("/Principal/CrearProductos");
                });

               post("/EliminarProductos", ctx -> {
                    
                    String id = ctx.formParam("id_Producto");
                    Producto auxProducto = Almacen.getInstance().buscarProductoByID(id);
                    auxAlmacen.eliminarProducto(auxProducto);

                    ctx.redirect("/Admin/ListadoProductos/");
                });
                


                post("/AgregarCarrito/:id", ctx -> {

            
                    String aux = ctx.pathParam("id",String.class).get();
                   
                        int cantidad =0;
                        try
                        {
                            if(ctx.formParam("qty") != null)
                            cantidad  = Integer.parseInt(ctx.formParam("qty"));
                        }
                        catch (NumberFormatException e)
                        {
                            cantidad = 0;
                        }

                    Producto auxProducto = Almacen.getInstance().getProductoByIDDB(aux);
                    Producto aux1 = new Producto(auxProducto.getId_Product(),auxProducto.getName_Product(),auxProducto.getPrice_Product(),auxProducto.getQty_Product() + cantidad);
                    
                    auxCarrito.addProducto(aux1);
                    auxCarrito.setCantidadArticulosCarrito(cantidad);

                    ctx.redirect("/Principal/ListadoProductos");
                });


                get("/CarritoCompra", ctx -> {
                    ArrayList<Producto> auxProductos = auxCarrito.getMisProductos();
                    Map<String, Object> modelo = new HashMap<>();

                    int x = auxCarrito.getCantidadArticulosCarrito();
                    modelo.put("cantidad_Carrito", x);
                    modelo.put("titulo", "Carro Compra");
                    modelo.put("auxProductos", auxProductos);
                    modelo.put("usuario",Almacen.getInstance().getNormal_user());
                    modelo.put("admin",Almacen.getInstance().getCurrentUser());
                    modelo.put("usuario1", ctx.sessionAttribute("usuario"));
                    modelo.put("total_carrito", auxCarrito.totalCarrito());

                    ctx.render("/publico/templates/CarroCompra.html",modelo);
                });

                post("/EliminarCarrito/:id", ctx -> {

            
                    String aux = ctx.pathParam("id",String.class).get();
                    auxCarrito.getMisProductos().remove(auxCarrito.buscarProductoByID(aux));

                    ctx.redirect("/CarritoCompra");
                });


                post("/ProcesarCompra", ctx -> {

                    String name = ctx.formParam("nombreCliente");
                    Ventas miVenta = new Ventas(name, auxCarrito, auxCarrito.totalCarrito());
                    
                    Almacen.getInstance().getMisVentas().add(miVenta);
                    System.out.println(miVenta.getId_Venta());
                    Almacen.getInstance().procesarCompraDB(miVenta);
                    ctx.sessionAttribute("carrito",null);

                    ctx.redirect("/Principal/ListadoProductos");

                    
                });


                get("/Ventas", ctx -> {
                    
                
                    Map<String, Object> modelo = new HashMap<>();

                    int x = auxCarrito.getCantidadArticulosCarrito();
                    modelo.put("misVentas",Almacen.getInstance().getMisVentasDB());
                    // modelo.put("nombre_Cliente", aux.getNombreCiente);
                    modelo.put("cantidad_Carrito", x);
                    modelo.put("titulo", "Ventas");
                    modelo.put("usuario",Almacen.getInstance().getNormal_user());
                    modelo.put("user",Almacen.getInstance().getCurrentUser());
                    modelo.put("usuario1", ctx.sessionAttribute("usuario"));
                    modelo.put("total_carrito", auxCarrito.totalCarrito());
                    

                
                    ctx.render("/publico/templates/Ventas.html",modelo);
                });





                });

            });
        }
}
