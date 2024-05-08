
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
public class MaladieComplexe {
    
    Connexion connecter;
    String requette;
    Statement statement;
    PreparedStatement pStatement;
    ResultSet resultSet;
    Scanner scan;
    String touche;
    
    public MaladieComplexe()
    {        
        connecter = new Connexion();
    }
    
    public void modifierMedecinAssistant()
    {
        try 
        {
            scan = new Scanner(System.in);
            int numero;
            int numMedAssist1;
            int numMedAssist2;
            
            System.out.println("Quel est le numéro de la consultation complexe que vous souhaitez modifier ?");
            numero = scan.nextInt();
            
            System.out.println("Quel est le numero du Premier médecin assistant ?");
            numMedAssist1 = scan.nextInt();
            
            System.out.println("Quel est le numero du Second médecin assistant ?");
            numMedAssist2 = scan.nextInt();
            
            requette = "UPDATE MaladiesComplexes SET numMedecinAssistant1 = '"+numMedAssist1+"' WHERE numMaladieC ="+numero+";";
            String requette1 = "UPDATE MaladiesComplexes SET numMedecinAssistant2 = '"+numMedAssist2+"' WHERE numMaladieC ="+numero+";";
            statement = connecter.connecterBD().createStatement();
            statement.executeUpdate(requette);
            statement.executeUpdate(requette1);
            connecter.connecterBD().close();

            System.out.println("");
            System.out.println("Les données ont été mises à jour !");
            
            System.out.println("");
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuMaladieComplexe();  
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }
    }
    
    /*
        Affichage les consultations consultations ainsi que les medecins qui ont donné à faire le diagnostic
    */ 
    public void afficherMaladieComplexe()
    {
        try 
        {
            statement = connecter.connecterBD().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM MaladiesComplexes;");
            System.out.println("LA LISTE DES MALADIES COMPLEXES");
            System.out.println("");
            System.out.print("Identifiant" +"\t");
            System.out.print("numConsult" +"\t");
            System.out.print("numMedecin" +"\t");
            
            System.out.print("Assistant 1" +"\t");
            System.out.print("Assistant 2" +"\t");
            System.out.println("Diagnostic");
            System.out.println("");     
            
            while(resultSet.next())
            {
                System.out.print("    "+resultSet.getInt("numMaladieC") +"\t"+"\t");
                System.out.print("    "+resultSet.getInt("numConsult")+"\t"+"\t");
                System.out.print("    "+resultSet.getInt("numMedecin")+"\t"+"\t");
                
                System.out.print("    "+resultSet.getInt("numMedecinAssistant1")+"\t"+"\t");
                System.out.print("    "+resultSet.getInt("numMedecinAssistant2")+"\t"+"\t");
                System.out.print(resultSet.getString("diagnostic"));
                System.out.println("");
                System.out.println("");
            }
            
            connecter.connecterBD().close();
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuMaladieComplexe();            
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }        
    }
    
    public void menuMaladieComplexe()
    {
        System.out.println("###########################");
        System.out.println("#### MALADIES COMPLEXES ###");
        System.out.println("###########################");
        System.out.println("#### 1. AFFICHER        ###");
        System.out.println("#### 2. MODIFIER        ###");
        System.out.println("#### 3. SUPPRIMER       ###");
        System.out.println("#### 4. RETOUR          ###");
        System.out.println("###########################");
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
                        afficherMaladieComplexe();
                        break;
            }
            case 2 -> {  
                        modifierMedecinAssistant();
                        break;
            }
            case 3 -> {
                        supprimerMaladieComplexe();
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
        Suppression d'une Consultation donnée
    */ 
    public void supprimerMaladieComplexe()
    {
        try 
        {
            scan = new Scanner(System.in);
            int numero;
            
            System.out.println("Quel est le numéro de la Consultation complexe à supprimer ?");
            numero = scan.nextInt();
            
            requette = "DELETE FROM MaladiesComplexes WHERE numMaladieC = "+numero+"";
            
            statement = connecter.connecterBD().createStatement();
            statement.executeUpdate(requette);
            connecter.connecterBD().close();
            
            System.out.println("");
            System.out.println("La consultation "+numero+" a été supprimée avec succès !");
            
            System.out.println("");
            System.out.println("");
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuMaladieComplexe();

        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }        
    }    
}
