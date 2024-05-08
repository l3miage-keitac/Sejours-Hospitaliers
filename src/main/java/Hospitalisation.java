
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cirekeita
 */
public class Hospitalisation {
    
    Connexion connecter;
    Statement statement;
    PreparedStatement pStatement;
    ResultSet resultSet;
    String requette;
    String touche;
    Scanner scan;
    
    public Hospitalisation()
    {        
        connecter = new Connexion();        
    }
    
    /*
        Insertion à la fin de la table Hospitalisation tout en respectant les contraintes

        Etat final: on a Table Hospitalisation+1
    */
    public void ajouterHospitalisation()
    {
        try 
        {
            scan = new Scanner(System.in);
            int numConsult;
            int numCham;
            LocalDate dateEntree;
            LocalDate dateSortie;
            
            System.out.println("Quel est le numéro de la Consultation associée ?");
            numConsult = scan.nextInt();
            
            System.out.println("Quel est le numéro de la Chambre assignée ?");
            numCham = scan.nextInt();
            
            System.out.println("Veuillez saisir la date d'Entrée sous cette forme : AAAA-MM-JJ");
            dateEntree = LocalDate.parse(scan.next());
            
            System.out.println("Veuillez saisir la date de Sortie sous cette forme : AAAA-MM-JJ");
            dateSortie = LocalDate.parse(scan.next());
            
            requette = "INSERT INTO Hospitalisation(numConsult, numCham, dateEntree, dateSortie) VALUES ("+numConsult+","+numCham+",'"+dateEntree+"','"+dateSortie+"')";
            
            pStatement = connecter.connecterBD().prepareStatement(requette);
            pStatement.executeUpdate();
            
            System.out.println("");
            System.out.println("L'hospitalisation  a été ajouté avec succès !");
            
            System.out.println("");
            System.out.println("");
            
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuHospitalisation();
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }
    }

    /*
        Affichage toutes les Hospitalisations de la Base de données
    */ 
    public void afficherHospitalisation()           //Affichage de toutes les hospitalisations de la table
    {
        try 
        {
            requette = "SELECT * FROM Hospitalisation;";
            
            statement = connecter.connecterBD().createStatement();
            resultSet = statement.executeQuery(requette);
            
            System.out.println("             LA LISTE DES HOSPITALISATIONS");
            System.out.println("");
            System.out.print("Indentifiant"+"\t");
            System.out.print("numConsult"+"\t");
            System.out.print("numChambre"+"\t");
            System.out.print("dateEntree"+"\t");
            System.out.println("dateSortie");
            System.out.println("");
            
            while(resultSet.next())
            {
                System.out.print("    "+resultSet.getInt("numHospi")+"\t"+"\t");
                System.out.print("    "+resultSet.getInt("numConsult")+"\t"+"\t");
                System.out.print("    "+resultSet.getInt("numCham")+"\t"+"\t");
                System.out.print(resultSet.getString("dateEntree")+"\t");
                System.out.print(resultSet.getString("dateSortie"));
                System.out.println("");
                System.out.println("");
            }
            connecter.connecterBD().close();
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuAfficherHospitalisation();
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }        
    }
    
    public class CoutHospi{
       int numP;
       float prixCham, coutConsult;
       String nomP, prenomP;
       LocalDate dateEntree, dateSortie;
       
       public CoutHospi(int numP, String nomP, String prenomP, LocalDate dateEntree, LocalDate dateSortie, float prixCham, float coutConsult){
           this.numP = numP;
           this.nomP = nomP;
           this.prenomP = prenomP;
           this.dateEntree = dateEntree;
           this.dateSortie = dateSortie;
           this.prixCham = prixCham;
           this.coutConsult = coutConsult;
       }
       
   }
    
    public ArrayList<CoutHospi> coutHospitalisationPatient()
    {
        ArrayList<CoutHospi> listeDesCouts = new ArrayList<>();
        try 
        {
            requette = "SELECT DISTINCT Patient.numPatient, Patient.nomP, Patient.prenomP, dateEntree, dateSortie, prix, coutConsult FROM Patient, Hospitalisation, Chambre, Consultation WHERE Patient.numPatient = Consultation.numPatient AND Consultation.numConsult = Hospitalisation.numConsult AND Chambre.numCham = Hospitalisation.numCham;";
            
            statement = connecter.connecterBD().createStatement();
            resultSet = statement.executeQuery(requette);
            System.out.println("    LA LISTE DES PATIENTS HOSPITALISÉS");
            
            System.out.println("");
            System.out.println("");
            System.out.print("Identifiant"+"\t");
            System.out.print("Nom"+"\t"+"\t");
            System.out.print("Prenom"+"\t"+"\t");
            System.out.println("CoutTotal");
            
            System.out.println("");
            
            
            while(resultSet.next())
            {
                float prix = resultSet.getFloat("prix");
                float coutCon = resultSet.getInt("coutConsult");
                Period period = Period.between(LocalDate.parse(resultSet.getString("dateEntree")), LocalDate.parse(resultSet.getString("dateSortie")));

                int days = period.getDays();
                float cout = days*prix;
                float coutTotal = cout + coutCon;

                System.out.print("    "+resultSet.getInt("numPatient")+"\t"+"\t");
                System.out.print(resultSet.getString("nomP")+"\t"+"\t");
                System.out.print(resultSet.getString("prenomP")+"\t"+"\t");
                System.out.println(" "+coutTotal); 
                
                System.out.println("");
                
            }
            System.out.println("");
            System.out.println("");
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuAfficherHospitalisation();
        } catch (SQLException e) {
        }
        return listeDesCouts;       
    }

    /*
        Modification de la Date de Sortie d'une Hospitalisation donnée
    */       
    public void modifierDateSortie()
    {
        try 
        {
            int numero;
            LocalDate dateSortie;
            scan = new Scanner(System.in);
            
            System.out.println("Quel est le numéro de l'hospitalisation a modifier ?");
            numero = scan.nextInt();
            System.out.println("Quelle est la nouvelle date de Sortie ? (AAAA-MM-JJ)");
            dateSortie = LocalDate.parse(scan.next());
            
            requette = "UPDATE Hospitalisation SET dateSortie = '"+dateSortie+"' WHERE numHospi = "+numero+";";
            statement = connecter.connecterBD().createStatement();
            statement.executeUpdate(requette);
            connecter.connecterBD().close();

            System.out.println("");
            System.out.println("Les données ont été mises à jour !");
            
            System.out.println("");
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuHospitalisation();  
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }        
    }
    
    public void menuHospitalisation()
    {
        System.out.println("########################");
        System.out.println("#### HOSPITALISATION ###");
        System.out.println("########################");
        System.out.println("#### 1. AFFICHER     ###");
        System.out.println("#### 2. AJOUTER      ###");
        System.out.println("#### 3. MODIFIER     ###");
        System.out.println("#### 4. SUPPRIMER    ###");
        System.out.println("#### 5. RETOUR       ###");
        System.out.println("########################");
        System.out.println("");    
        
        scan = new Scanner(System.in);
        System.out.println("Quelle action voulez-vous effectuer ? ");
        String action;
        char c;
        int choix;
        do { 
            System.out.println("Veuillez saisir un entier entre 1 et 5 ! ");
                        
            action = scan.next();
            c = action.charAt(0);
            choix = Character.getNumericValue(c);
            
        } while (choix != 1 & choix != 2 & choix != 3 & choix != 4 & choix != 5);

        switch (choix) {
            case 1 -> {
                        menuAfficherHospitalisation();
                        break;
            }
            case 2 -> {  
                        ajouterHospitalisation();
                        break;
            }
            case 3 -> {
                        modifierDateSortie();
                        break;
            }
            case 4 ->{
                        supprimerHospitalisation();
                        break;
            }
            case 5 ->{
                        Hopital.menuPrincipal();
                        break;
            }
            default -> throw new AssertionError();
            }
    }
    
    public void menuAfficherHospitalisation()
    {
        System.out.println("##################################");
        System.out.println("#### AFFICHER HOSPITALISATION  ###");
        System.out.println("##################################");
        System.out.println("#### 1. Hospitalisation        ###");
        System.out.println("#### 2. Cout d'hospitalisation ###");
        System.out.println("#### 3. RETOUR                 ###");
        System.out.println("##################################");
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
            
        } while (choix != 1 & choix != 2 & choix != 3);
        
        switch (choix) {
            case 1 ->{
                        afficherHospitalisation();
                        break;
            }
            case 2 -> {  
                        coutHospitalisationPatient();
                        break;
            }
            case 3 -> {
                        menuHospitalisation();
                        break;
            }
            default -> throw new AssertionError();
        }       
    }
    
    /*
        Suppression d'une Hospitalisation donnée
    */        
    public void supprimerHospitalisation()         
    {
        try 
        {
            scan = new Scanner(System.in);
            int numero;
            
            System.out.println("Quel est le numéro de l'hospitalisation a supprimer ?");
            numero = scan.nextInt();
            
            requette = "DELETE FROM Hospitalisation WHERE numHospi = "+numero+"";
            statement = connecter.connecterBD().createStatement();
            statement.executeUpdate(requette);
            connecter.connecterBD().close();
            
            System.out.println("");
            System.out.println("L'hospitalisation "+numero+" a été supprimé avec succès !");
            System.out.println("");
            System.out.println("");
            
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuHospitalisation();
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }        
    }
}
