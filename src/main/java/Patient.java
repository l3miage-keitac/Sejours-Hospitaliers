
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
public class Patient {
    
    Connexion connecter;
    String requette;
    Statement statement;
    PreparedStatement pStatement;
    ResultSet resultSet;
    Scanner scan;
    String touche;
    
    public Patient()
    {
        connecter = new Connexion();        
    }
    /*
    Insertion à la fin de la table Patient tout en respectant les contraintes
    
    Etat final on a Table Patient+1
    */
    public void ajouterPatient()
    {
        try 
        {
            String nomP;
            String prenomP;
            String dateNaiss;
            scan = new Scanner(System.in);
            
            System.out.println("Quel est le Nom du patient a ajouter ?");
            nomP = scan.next();
            
            System.out.println("Quel est le Prénom du patient a ajouter ?");
            prenomP = scan.next();
            
            System.out.println("Veuillez saisir sa date de naissance sous cette forme : AAAA-MM-JJ");
            dateNaiss = scan.next();
            
            requette = "INSERT INTO Patient(nomP, prenomP, dateNaiss) VALUES ('"+nomP+"','"+prenomP+"','"+dateNaiss+"')";
            pStatement = connecter.connecterBD().prepareStatement(requette);
            pStatement.executeUpdate();
            connecter.connecterBD().close();
            
            System.out.println("");
            System.out.println("Le patient a été ajouté avec succes !");
           
            System.out.println("");
            System.out.println("");
            
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuPatient();
                     
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }       
    }

    /*
        Affichage de tous les elements de la table Patient
    */     
    public void afficherPatient()
    {
        try {
            
            statement = connecter.connecterBD().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Patient;");
            System.out.println("          LA LISTE DES PATIENTS");
            System.out.println("");
            System.out.print("Identifiant"+"\t");            
            System.out.print("Nom"+"\t"+"\t");
            System.out.print("Prénom"+"\t"+"\t");
            System.out.print("dateNaiss");
            System.out.println("");
            System.out.println("");
            
            while(resultSet.next())
            {
                System.out.print("    "+resultSet.getInt("numPatient")+"\t"+"\t");
                
                System.out.print(resultSet.getString("nomP")+"\t"+"\t");
                System.out.print(resultSet.getString("prenomP")+"\t"+"\t");
                System.out.print(resultSet.getString("dateNaiss"));
                
                
                System.out.println(""); 
                System.out.println("");
            }
            
            connecter.connecterBD().close();
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuPatient();
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }        
    }
    
    public void menuPatient()
    {
        System.out.println("######################");
        System.out.println("####    PATIENT    ###");
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
                        afficherPatient();
                        break;
            }
            case 2 -> {  
                        ajouterPatient();
                        break;
            }
            case 3 -> {
                        supprimerPatient();
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
        Suppression d'un Patient donné
    */ 
    public void supprimerPatient()
    {
        try {
            
            int numero;
            scan = new Scanner(System.in);
            
            System.out.println("Quel est le numéro du patient à supprimer ?");
            numero = scan.nextInt();
            
            requette = "DELETE FROM Patient WHERE numPatient = "+numero+"";
            statement = connecter.connecterBD().createStatement();
            statement.executeUpdate(requette);
            connecter.connecterBD().close();
            
            System.out.println("");
            System.out.println("Le Patient "+numero+" a été supprimé !");
            System.out.println("");
            System.out.println("");
            
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuPatient();
                       
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }
        
    }
}
