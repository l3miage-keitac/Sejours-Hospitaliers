
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cirekeita
 */
public class Connexion {
    Connection connecter;
    public Connection connecterBD(){
        String url = "jdbc:sqlite:gestionSejoursHospitaliers.db";
        try 
        {
            connecter = DriverManager.getConnection(url);
           // System.out.println("Connexion reussie !");
        } catch (SQLException e) 
            {
                System.err.println(e.getMessage());
            }
        return connecter;
        
    }
    
}
