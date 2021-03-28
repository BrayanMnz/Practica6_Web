package PracticasISC464_Practica2.Controllers;

import PracticasISC464_Practica2.util.BaseControlador;
import PracticasISC464_Practica2.Classes.*;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import java.util.*;
import static io.javalin.apibuilder.ApiBuilder.*;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class UsersController extends BaseControlador{
    
    public UsersController(Javalin app){
        super(app);
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
    }

    Almacen auxAlmacen = Almacen.getInstance();

    @Override
    public void aplicarRutas() {
        app.routes(() -> {

            before(ctx -> {
                CarroCompra auxCarrito = ctx.sessionAttribute("carrito");
                if(auxCarrito == null) {
                    ArrayList<Producto> auxProductos = new ArrayList<Producto>();
                    ctx.sessionAttribute("carrito", new CarroCompra("001", auxProductos));
                }

    
                 if(ctx.cookie("recuerdame") != null){
                    for(Usuario auxUsuario : auxAlmacen.getListaUsuariosDB()) {
                        String key = "@cm1ptgrone" + auxUsuario.getUser();
                        StrongPasswordEncryptor pwEncryptor = new StrongPasswordEncryptor();
                        String encryptedPassword = pwEncryptor.encryptPassword(key);
                        if(pwEncryptor.checkPassword(encryptedPassword, ctx.cookie("recuerdame"))){
                            auxAlmacen.login_User(auxUsuario.getUser(), auxUsuario.getPassword());
                            ctx.sessionAttribute("usuario", auxUsuario);
                        }
                    }
                }




            });

            path("/Practica_2/usuarios", () -> {
                
                get("/login", ctx->{
                    CarroCompra auxCarrito = ctx.sessionAttribute("auxCarrito");
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Login");
                    ctx.render("/publico/templates/login.html",modelo);
                });


                post("/login", ctx->{
                    CarroCompra auxCarrito = ctx.sessionAttribute("auxCarrito");
                  //  Map<String, Object> modelo = new HashMap<>();
                    String user = ctx.formParam("usrname");
                    String passwrd = ctx.formParam("password");
                    String nombre = ctx.formParam("name");

                    Usuario auxUsuario = auxAlmacen.login_User(user,passwrd);

                    if(ctx.formParam("remember") != null){
                        String key = "@cm1ptgrone" + auxUsuario.getUser();
                        StrongPasswordEncryptor pwEncryptor = new StrongPasswordEncryptor();
                        String encryptedPassword = pwEncryptor.encryptPassword(key);
                        ctx.cookie("recuerdame", encryptedPassword, 604800);
                    } 
                    ctx.sessionAttribute("usuario", auxUsuario);

                    ctx.redirect("/Principal/Admin/ListadoProductos");
                });


                get("/logout", ctx-> {
                    auxAlmacen.logout();
                    ctx.req.getSession().invalidate();
                    ctx.redirect("Practica_2/usuarios/login");
                });

            });

            
        });
    }






}