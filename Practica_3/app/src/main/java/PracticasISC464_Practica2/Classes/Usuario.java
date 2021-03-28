package PracticasISC464_Practica2.Classes;
import java.util.Objects;
import java.util.*;

public class Usuario {

    private String user;
    private String name;
    private String password;


    public Usuario(String user, String name, String password) {
        this.user = user;
        this.name = name;
        this.password = password;
    }


    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


   


}
