package PracticasISC464_Practica2.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DataBaseServices {

    private static DataBaseServices instancia;
    private String URL = "jdbc:h2:tcp://localhost/~/practica_3_BrayanMnz"; //Modo Server...

    /**
     *Implementando el patron Singleton
     */
    private  DataBaseServices(){
        registrarDriver();
    }

    /**
     * Retornando la instancia.
     * @return
     */
    public static DataBaseServices getInstance(){
        if(instancia==null){
             instancia = new DataBaseServices();
        }
        return instancia;
    }

    /**
     * Metodo para el registro de driver de conexión.
     */
    private void registrarDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }

    public void testConexion() {
        try {
            getConexion().close();
            System.out.println("Conexión realizado con exito...");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }


}