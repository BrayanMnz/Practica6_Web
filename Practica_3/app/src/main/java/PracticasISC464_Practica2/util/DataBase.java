package PracticasISC464_Practica2.util;
import org.h2.tools.Server;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class DataBase {

    private static Server server;

    private static DataBase db;


    public static DataBase getInstance() {
        if(db == null){
            db = new DataBase();
        }
        return db;
    }

    /**
     *
     * @throws SQLException
     */
    public void startDb() throws SQLException {
        server = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-ifNotExists").start();
    }

    /**
     *
     * @throws SQLException
     */
    public  void stopDb() throws SQLException {
        server.shutdown();
    }



    /**
     * Metodo para recrear las tablas necesarios
     * @throws SQLException
     */
    public void crearTablas() throws SQLException{
  
        String usr = "CREATE TABLE IF NOT EXISTS Usuario(user VARCHAR(50) PRIMARY KEY,  full_name VARCHAR(255), password VARCHAR(255));";

        String prd = "CREATE TABLE IF NOT EXISTS Productos(id_Product VARCHAR(50) PRIMARY KEY NOT NULL,  name_Product VARCHAR(255), price_Product DECIMAL NOT NULL, qty_Product INT );";

        String vnts = "CREATE TABLE IF NOT EXISTS Ventas(id_Venta INT IDENTITY PRIMARY KEY NOT NULL, fecha_Venta DATE , nombre_Cliente VARCHAR(255) NOT NULL, subTotal DECIMAL NOT NULL, Total DECIMAL NOT NULL);";

        String ProdVenta = "CREATE TABLE IF NOT EXISTS PRODVENTA (ID_PRODUCTO VARCHAR(255) NOT NULL, " +
                "ID_VENTA VARCHAR(255) NOT NULL, CANTIDAD INT NOT NULL, PRIMARY KEY (ID_VENTA, ID_PRODUCTO), " +
                "CONSTRAINT FK_PRODVENTA_VENTA FOREIGN KEY (ID_VENTA) REFERENCES VENTAS(ID_VENTA), " +
                "CONSTRAINT FK_PRODVENTA_PRODUCTO FOREIGN KEY (ID_PRODUCTO) REFERENCES PRODUCTOS(ID_PRODUCT));";

     Connection con = DataBaseServices.getInstance().getConexion();
     Statement statement = con.createStatement();
     statement.execute(usr);
     statement.execute(prd);
     statement.execute(vnts);
     statement.close();
     con.close();
 }

 // Creacion de los usuarios por defecto
 public void crearUsuarios(){
     try {
         Connection conn = DataBaseServices.getInstance().getConexion();
         String query = "MERGE INTO USUARIO VALUES(?, ?, ?);";
         PreparedStatement ps = conn.prepareStatement(query);
       

         ps.setString(1, "admin");
         ps.setString(2, "Brayan Muñoz");
         ps.setString(3, "admin");
         ps.executeUpdate();

     } catch (SQLException e) {
         e.printStackTrace();
     }
 }

}