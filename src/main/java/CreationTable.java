
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cirekeita
 */
public class CreationTable {
        
    Connexion connecter = new Connexion();
    Connection connec;
    String fichier;
    String text = "";
    Statement statement;        
    FileReader fileReader;
    File file;
    int charLu; 
    
    /*
    Cette classe se charge de la création de toutes les Tables de notre Base de données
    Elle prend en compte et execute le fichier (create.sql) de la Création
    ∫*/
    public CreationTable()
    {        
        fichier = "Query/create.sql";
        connec = connecter.connecterBD();
        
        try 
        {  
            file = new File(fichier);
            fileReader = new FileReader(file);
            charLu = fileReader.read();

            while(charLu != -1)
            {
                text += (char) charLu;
                charLu = fileReader.read();
            }      
        } 
        catch (IOException e) 
        {
            System.err.println(e.getMessage());
        }       
    }
    
    public void createNewTable(String sql) throws IOException 
    {
        
        try{
            statement = connec.createStatement();
            statement.execute(sql);
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void executeQuery() throws IOException
    {       
        String querys[];
        
        try{ 
            
            querys = text.split(";");            
            for (String query : querys) 
            {
               createNewTable(query);
            }
        } 
        catch (IOException e) 
        {
            System.err.println(e.getMessage());
        } 
    }    
}
