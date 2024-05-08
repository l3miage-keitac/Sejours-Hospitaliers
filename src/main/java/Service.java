
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cirekeita
 */
public class Service {
    
    Connexion connecter;
    PreparedStatement pStatement;
    Statement statement;
    ResultSet resultSet;
    Scanner scan;
    String requette;
    String touche;
    
    public Service()
    {        
        connecter = new Connexion();            
    }
    
    /*
        Insertion à la fin de la table Service tout en respectant les contraintes

        Etat final: on a Table Service+1
    */    
    public void ajouterService()
    {
        try 
        {
            String libelle;           
            scan = new Scanner(System.in);
                                    
            System.out.println("Quel est le nom / libelle du service a ajouter ?");            
            libelle = scan.next();
           
            requette = "INSERT INTO Service (libelle) VALUES ('"+libelle+"')";
            
            pStatement = connecter.connecterBD().prepareStatement(requette);
            pStatement.executeUpdate();
            
            System.out.println("");
            System.out.println("Les données ont été insérées dans la table avec succès !");
            System.out.println("");
            System.out.println("");
            
            connecter.connecterBD().close();
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuService();  
        } 
        catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }        
    }
    
    /*
        Affichage de tous les elements de la table Service
    */      
    public void afficherService()
    {
        try 
        {
            statement = connecter.connecterBD().createStatement();
            resultSet = statement.executeQuery("SELECT numService, libelle FROM Service");
            System.out.println("LA LISTE DES SERVICES");
            System.out.println("");
            System.out.print("Identifiant" +"\t");
            System.out.println("Libelle");
            System.out.println("");
            
            while(resultSet.next()){
                System.out.print("    "+resultSet.getInt("numService")+"\t"+"\t");
                System.out.print(resultSet.getString("libelle"));
                System.out.println("");
                System.out.println("");
            }    
            connecter.connecterBD().close();
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuService();
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }
        
    }

    public void menuService()
    {
        System.out.println("######################");
        System.out.println("####    SERVICE    ###");
        System.out.println("######################");
        System.out.println("#### 1. AFFICHER   ###");
        System.out.println("#### 2. AJOUTER    ###");
        System.out.println("#### 3. SUPPRIMER  ###");
        System.out.println("#### 4. RETOUR     ###");
        System.out.println("######################");
        System.out.println("");
                                
        scan = new Scanner(System.in);
        System.out.println("Quelle action voulez-vous effectuer ? ");
        String action;
        char c;
        int choix;
        do { 
            System.out.println("Veuillez saisir un entier entre 1 et 4 ! ");
                        
            action = scan.next();
            c = action.charAt(0);
            choix = Character.getNumericValue(c);
            
        } while (choix != 1 & choix != 2 & choix != 3 & choix != 4);
        
        switch (choix) {
            case 1 -> {
                        afficherService();
                        break;
            }
            case 2 -> {  
                        ajouterService();
                        break;
            }
            case 3 -> {
                        supprimerService();
                        break;
            }
            case 4 ->{
                        Hopital.menuPrincipal();
                        break;
            }

            default -> throw new AssertionError();
            }
    }
    
    /*
        Suppression d'un Service donné
        A noter que cette suppression n'est possible que si cet n'est pas utilisé dans la table Medecin ou Chambre
    */ 
    public void supprimerService()
    {
        try 
        {   
            int numero;
            
            scan = new Scanner(System.in);
            
            System.out.println("Quel est le numéro du Service que vous souhaitez supprimer ?");
            numero = scan.nextInt();
            
            requette = "DELETE FROM Service WHERE numService = "+numero+"";            
            statement = connecter.connecterBD().createStatement();
            statement.executeUpdate(requette);
            connecter.connecterBD().close();
            
            System.out.println("");
            System.out.println("Le service "+numero+ " a été supprimé");
            System.out.println("");
            System.out.println("");
            
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuService();
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }        
    }
}
