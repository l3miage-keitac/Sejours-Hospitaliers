
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
public class Medecin {
    
    Connexion connecter;
    Statement statement;
    PreparedStatement pStatement;
    ResultSet resultSet;
    String requette;
    String touche;
    Scanner scan;
          
    
    public Medecin()
    {       
        connecter = new Connexion();        
    }
    /*
    Insertion à la fin de la table Medecin tout en respectant les contraintes
    
    Etat final: on a Table Medecin+1
    */
    public void ajouterMedecin()
    {
        try 
        {
            scan = new Scanner(System.in);
            int numMedecin;
            String nomMed;
            String prenomMed;
            String specialite;
            int numService;
            
            System.out.println("Quel est le nom du Médecin à ajouter ?");
            nomMed = scan.next();
            
            System.out.println("Quel est le prénom du Médecin à ajouter ?");
            prenomMed = scan.next();
            
            System.out.println("Quel est la spécialité du Médecin à ajouter ?");
            scan.nextLine();  //On vide la ligne avant d'en lire une autre
            specialite = scan.nextLine();
            
            System.out.println("Quel est le numéro du service dans lequel il travaille ?");
            numService = scan.nextInt();
            
            //requette = "INSERT INTO Medecin VALUES (?, ?, ?, ?, ?)";
            requette = "INSERT INTO Medecin(nomMed, prenomMed, specialite, numService) VALUES('"+nomMed+"','"+prenomMed+"','"+specialite+"',"+numService+")";
            
            pStatement = connecter.connecterBD().prepareStatement(requette);
            pStatement.executeUpdate();
            connecter.connecterBD().close();
            
            System.out.println("");
            System.out.println("Le médecin a été ajouté avec succès !");

            System.out.println("");
            System.out.println("");
            
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuMedecin();
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }        
    }
 
    /*
        Affichage de tous les elements de la table Medecin
    */  
    public void afficherMedecin()
    {
        try 
        {
            statement = connecter.connecterBD().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Medecin;");
            System.out.println("LA LISTE DES MEDECINS");
            System.out.println("");
            System.out.print("Identifiant" +"\t");
            System.out.print("Nom" +"\t"+"\t");  
            System.out.print("Prenom"  +"\t"+"\t");
            System.out.print("numService"+"\t");
            System.out.println("Spécialité");
                        
            System.out.println("");
            
            while(resultSet.next()){
                System.out.print("    "+resultSet.getInt("numMedecin") +"\t"+"\t");
                System.out.print(resultSet.getString("nomMed")+"\t"+"\t");
                System.out.print(resultSet.getString("prenomMed")+"\t"+"\t");
                System.out.print("    "+resultSet.getInt("numService")+"\t"+"\t");                
                System.out.print(resultSet.getString("specialite"));
                                
                System.out.println("");
                System.out.println("");
            }
           
            connecter.connecterBD().close();
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuAfficherMedecin();
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }        
    }
    
    public void afficherMedecinParService()  //Question posée: Combien de médecins par service ?
    {        
        try 
        {
            requette = "SELECT libelle, COUNT(numMedecin) AS nbMed FROM Service JOIN Medecin USING(numService) GROUP BY libelle ORDER BY nbMed DESC;";
            
            statement = connecter.connecterBD().createStatement();
            resultSet = statement.executeQuery(requette);
            System.out.print("NombreMedecin"+"\t");
            System.out.print("Service" );
            
            System.out.println("");
            System.out.println("");
            
            while(resultSet.next()){
                System.out.print("     "+resultSet.getInt("nbMed")+"\t"+"\t");
                System.out.print(resultSet.getString("libelle"));
                
                System.out.println("");
                System.out.println("");
            }
            connecter.connecterBD().close();
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuAfficherMedecin();            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }        
    }
    
    public void menuAfficherMedecin()
    {
        System.out.println("################################");
        System.out.println("####     AFFICHER MEDECIN    ###");
        System.out.println("################################");
        System.out.println("#### 1. Tous les Medecins    ###");
        System.out.println("#### 2. Medecins par Service ###");
        System.out.println("#### 3. RETOUR               ###");
        System.out.println("################################");
        System.out.println("");
                                
        scan = new Scanner(System.in);
        System.out.println("Quelle action voulez-vous effectuer ? ");
        String action;
        char c;
        int choix;
        do { 
            System.out.println("Veuillez saisir un entier entre 1 et 3 ! ");
                        
            action = scan.next();
            c = action.charAt(0);
            choix = Character.getNumericValue(c);
            
        } while (choix != 1 & choix != 2 & choix != 3 );
        
        switch (choix) {
            case 1 ->{
                        afficherMedecin();
                        break;
            }
            case 2 -> {  
                        afficherMedecinParService();
                        break;
            }
            case 3 -> {
                        menuMedecin();
                        break;
            }
            default -> throw new AssertionError();
        }       
    }
    
    public void menuMedecin()
    {
        System.out.println("#####################");
        System.out.println("####    MEDECIN   ###");
        System.out.println("#####################");
        System.out.println("#### 1. AFFICHER  ###");
        System.out.println("#### 2. AJOUTER   ###");
        System.out.println("#### 3. MODIFIER  ###");
        System.out.println("#### 4. SUPPRIMER ###");
        System.out.println("#### 5. RETOUR    ###");
        System.out.println("#####################");
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
            
        } while (choix != 1 & choix != 2 & choix != 3 & choix != 4 & choix != 5);
        
        switch (choix) {
            case 1 -> {
                        menuAfficherMedecin();
                        break;
            }
            case 2 -> {  
                        ajouterMedecin();
                        break;
            }
            case 3 -> {
                        modifierServiceMedecin();
                        break;
            }
            case 4 ->{
                        supprimerMedecin();
                        break;
            }
            case 5 ->{
                        Hopital.menuPrincipal();
                        break;
            }
            default -> throw new AssertionError();
            }
    }
    
    public void modifierServiceMedecin()
    {
        try {
            scan = new Scanner(System.in);
            int numero;
            int numService;
            
            System.out.println("Quel est l'identifiant du Médecin à modifier ?");
            numero = scan.nextInt();
            
            System.out.println("Quel est le numéro du nouveau Service dans lequel il est affecté ?");
            numService = scan.nextInt();
            
            requette = "UPDATE Medecin SET numService = '"+numService+"' WHERE numMedecin = "+numero+"";
            statement = connecter.connecterBD().createStatement();
            statement.executeUpdate(requette);
            connecter.connecterBD().close();
            
            System.out.println("");
            System.out.println("Les données ont été mises à jour !");
            
            System.out.println("");
            System.out.println("");
            
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuMedecin();
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }
        
    }

    /*
        Suppression d'un Medecin donné
    */     
    public void supprimerMedecin()
    {
        try 
        {
            int numero;
            scan = new Scanner(System.in);
            
            System.out.println("Quel est le numéro du Medecin à supprimer ?");
            numero = scan.nextInt();
            
            requette = "DELETE FROM Medecin WHERE numMedecin = "+numero+"";
            statement = connecter.connecterBD().createStatement();
            statement.executeUpdate(requette);
            
            System.out.println("");
            System.out.println("Le Medecin "+numero+" a été supprimé avec succès!");

            System.out.println("");
            System.out.println("");
            
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuAfficherMedecin();
                       
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }

    }
    
}
