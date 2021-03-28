package PracticasISC464_Practica2;

import PracticasISC464_Practica2.Classes.*;
import PracticasISC464_Practica2.Controllers.*;
import PracticasISC464_Practica2.util.*;
import java.sql.SQLException;
import io.javalin.Javalin;

public class App {

    public static void main(String[] args) throws SQLException {


        DataBase.getInstance().startDb();
        DataBaseServices.getInstance().testConexion();
        DataBase.getInstance().crearTablas();
        

        DataBase.getInstance().crearUsuarios();




     




        //Creando la instancia del servidor.
        Javalin app = Javalin.create(conf ->{
        conf.addStaticFiles("/publico"); //desde la carpeta de resources
        }).start(7000);
        //creando el manejador
        System.out.println("Server started at Port:  7000");
        app.get("/", ctx -> ctx.redirect("http://127.0.0.1:7000/Principal/ListadoProductos"));


        new UsersController(app).aplicarRutas();
        new mainController(app).aplicarRutas();
    }

}
