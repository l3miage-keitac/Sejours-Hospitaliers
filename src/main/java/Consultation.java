
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cirekeita
 */
public class Consultation {
    
    Connexion connecter;
    ResultSet resultSet;
    String requette;
    Statement statement;
    PreparedStatement pStatement;
    Scanner scan;
    String touche;
    
    
    public Consultation()
    {
        connecter = new Connexion();       
    }
    
    /*
    Insertion à la fin de la table Consultation tout en respectant les contraintes
    
    Etat final: on a Table Consultation+1
    Dans le cas d'une Consultation de type Complex, elle est aussi inserée dans la Table MaladiesComplexes
    */
    public void ajouterConsultation()           //Insertion d'une consultation dans la table
    {
        try 
        {
            scan = new Scanner(System.in);
            int numConsult;
            int numPatient;
            int numMedecin;
            String type;
            String diagnostic;
            LocalDate dateConsult;
            float coutConsult;
            
            System.out.println("Quel est l'identifiant de la consultation à ajouter ?");
            numConsult = scan.nextInt();
            
            System.out.println("Quel est le numéro du patient consulté ?");
            numPatient = scan.nextInt();
            
            System.out.println("Quel est l'identifiant du Médedin traitant ?");
            numMedecin = scan.nextInt();
            
            do {  
                System.out.println("Quel est le type de la consultation ? (Normal ou Complex)");
                type = scan.next();
            } while ((!type.equals("normal") ) & (!type.equals("Normal")) & (!type.equals("Complex")) & (!type.equals("complex")));
            
            System.out.println("Quel est le diagnostic posé ?");
            scan.nextLine();  //On vide la ligne avant d'en lire une autre
            diagnostic = scan.nextLine();

            
            System.out.println("Quel est la date de la consultation ? (AAAA-MM-JJ)");
            dateConsult = LocalDate.parse(scan.next());
            
            System.out.println("Quel est le coût de l'acte posé ?");
            coutConsult = scan.nextFloat();
                        
            requette = "INSERT INTO Consultation (numConsult, numPatient, numMedecin, type, diagnostic, dateConsult, coutConsult) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
            pStatement = connecter.connecterBD().prepareStatement(requette);
            pStatement.setInt(1, numConsult);
            pStatement.setInt(2, numPatient);
            pStatement.setInt(3, numMedecin);
            pStatement.setString(4, type);
            pStatement.setString(5, diagnostic);
            pStatement.setString(6, dateConsult.toString());
            pStatement.setFloat(7, coutConsult);
            // Exécuter la requête SQL de INSERT
            int colonneInsere = pStatement.executeUpdate();
            if (colonneInsere > 0) 
            {
                System.out.println("");
                
            //Verifier si Consultation Complexe ---> Insertion dans la Table MaladieComplexe
                //connecter.connecterBD().close();               
               
                if(type.contains("omplex"))
                {   
                    System.out.println("");
                    System.out.println("");
                    System.out.println("Quel est l'identifiant du Premier médecin assistant ?");
                    int numMedAssistant1 = scan.nextInt();

                    System.out.println("Quel est l'identifiant du Second médecin assistant ?");
                    int numMedAssistant2 = scan.nextInt();               

                    String requette1 = "INSERT INTO MaladiesComplexes (numConsult, numMedecin, diagnostic, numMedecinAssistant1, numMedecinAssistant2) VALUES ( ?, ?, ?, ?, ?)";
                    PreparedStatement pStatement1 = connecter.connecterBD().prepareStatement(requette1);
                    pStatement1.setInt(1, numConsult);
                    pStatement1.setInt(2,  numMedecin);
                    pStatement1.setString(3, diagnostic);
                    pStatement1.setInt(4, numMedAssistant1);
                    pStatement1.setInt(5, numMedAssistant2);
                    // Exécuter la requête SQL de INSERT
                    int rowsInserted = pStatement1.executeUpdate();
                    if (rowsInserted > 0) 
                    {
                        System.out.println("");
                        System.out.println("La Maladie a été ajoutée avec succès !");
                    } 
                    else 
                    {
                        System.out.println("Une erreur s'est produite lors de l'insertion des données dans la table.");
                    }
                }
            System.out.println("");
            System.out.println("Les données ont été insérées avec succès dans la table !");

            System.out.println("");
            System.out.println("");

            System.out.println("Tapez une touche pour revenir en arrière");

            scan = new Scanner(System.in);
            touche = scan.next();
            menuConsultation();

            } 
            else 
            {
                System.out.println("Une erreur s'est produite lors de l'insertion des données dans la table.");
            }           
            connecter.connecterBD().close();               
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }
    }

    /*
        Affichage de toutes les Consultations (Normale et Complexe)
    */ 
    public void afficherConsultation()          //Affichage de toutes les Consultations
    {
        try 
        {            
            statement = connecter.connecterBD().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Consultation;");
            System.out.println("LA LISTE DES CONSULTATIONS");
            System.out.println("");
            System.out.print("Identifiant" +"\t");
            System.out.print("numPatient" +"\t");
            System.out.print("numMedecin" +"\t");
            System.out.print("dateConsult" +"\t");
            System.out.print("coutConsult" +"\t");
            System.out.print("Type" +"\t" +"\t");
            System.out.println("Diagnostic");
                        
            System.out.println("");     
            
            while(resultSet.next())
            {
                System.out.print("    "+resultSet.getInt("numConsult") +"\t"+"\t");
                System.out.print("    "+resultSet.getInt("numPatient")+"\t"+"\t");
                System.out.print("    "+resultSet.getInt("numMedecin")+"\t"+"\t");
                System.out.print(resultSet.getString("dateConsult")+"\t");
                System.out.print(""+resultSet.getFloat("coutConsult")+"\t"+"\t");
                System.out.print(resultSet.getString("type")+"\t"+"\t");
                System.out.print(resultSet.getString("diagnostic"));
                
                System.out.println("");
                System.out.println("");
            }

            connecter.connecterBD().close();
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuAfficherConsultation();            
        } catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }        
    }

    /*
        Quels sont les diagnostics les plus fréquents ?
    */  
    public void afficherDiagnostic()            
    {
        try{    
            requette = "SELECT diagnostic,COUNT(*)AS Total FROM Consultation GROUP BY diagnostic ORDER BY Total DESC;";
            
            statement = connecter.connecterBD().createStatement();
            resultSet = statement.executeQuery(requette);
            System.out.println("LA LISTE DES DIAGNOSTICS LES PLUS FREQUENTS");
            System.out.println("");
            System.out.print("Total" +"\t");
            System.out.print("Diagnostic");
            System.out.println("");
            System.out.println("");
            
            while(resultSet.next()){
                System.out.print("  "+resultSet.getInt("Total") +"\t");
                System.out.print(resultSet.getString("diagnostic"));
                System.out.println("");
                System.out.println("");
            }
            connecter.connecterBD().close();
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuAfficherConsultation();            
        } catch (SQLException e) 
        {
        System.err.println(e.getMessage());
        }       
    }
    
    public void afficherNbConsultParMedecin()
    {
        try {
            requette = "SELECT numMedecin, nomMed, prenomMed, count(numConsult) AS TotalConsult FROM Consultation jOIN Medecin USING(numMedecin) GROUP BY numMedecin ORDER BY TotalConsult DESC;";
            statement = connecter.connecterBD().createStatement();
            resultSet = statement.executeQuery(requette);
            System.out.println("LE NOMBRE DE CONSULTATION TOTAL EFFECTUE PAR MEDECIN");
            System.out.println("");
            System.out.print("NombreConsult" +"\t");
            System.out.print("Identifiant" +"\t");
            System.out.print("Nom" +"\t"+"\t");
            System.out.print("Prenom");
            System.out.println("");
            System.out.println("");
            
            while(resultSet.next()){
                System.out.print("    "+resultSet.getInt("TotalConsult") +"\t"+"\t");
                System.out.print("    "+resultSet.getInt("numMedecin") +"\t"+"\t");
                System.out.print(resultSet.getString("nomMed")+"\t"+"\t");
                System.out.print(resultSet.getString("prenomMed"));
                System.out.println("");
                System.out.println("");
            }
            connecter.connecterBD().close();
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuAfficherConsultation();

        } catch (SQLException e) {
        }
    }

    /*
        Modification du Cout d'une Consultation donnée
    */     
    public void modifierCoutConsult()           //Modifier le prix d'une consultation
    {
        try 
        {
            int numero;
            float coutConsult;
            scan = new Scanner(System.in);
            
            System.out.println("Quel est le numero de la consultation à modifier ?");
            numero = scan.nextInt();
            
            System.out.println("Quel est le montant de mise à jour ?");
            coutConsult = scan.nextFloat();
            System.out.println("ici");
            requette = "UPDATE Consultation SET coutConsult = '"+coutConsult+"' WHERE numConsult = "+numero+";";
            statement = connecter.connecterBD().createStatement();
            statement.executeUpdate(requette);
            connecter.connecterBD().close();
          
            System.out.println("");
            System.out.println("Les données ont été mises à jour !");
            
            System.out.println("");
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuModifierConsultation();  
                             
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }       
    }
 
    /*
        Modification du Diagnostic d'une Consultation donnée
    */   
    public void modifierDiagnosticConsult()     //Modifier le diagnostic d'une consultation
    {
        try 
        {
            int numero;
            String diagnostic;
            scan = new Scanner(System.in);
            
            System.out.println("Quel est le numero de la consultation à modifier ?");
            numero = scan.nextInt();
            
            System.out.println("Quel est le nouveau diagnostic ?");
            scan.nextLine();  //On vide la ligne avant d'en lire une autre
            diagnostic = scan.nextLine();

            requette = "UPDATE Consultation SET diagnostic = '"+diagnostic+"' WHERE numConsult = "+numero+";";
            String requette1 = "UPDATE MaladiesComplexes SET diagnostic = '"+diagnostic+"' WHERE numConsult = "+numero+";";
            
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
            menuModifierConsultation();  
                             
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }       
    }
    
    public void menuConsultation()
    {
        System.out.println("######################");
        System.out.println("####  CONSULTATION ###");
        System.out.println("######################");
        System.out.println("#### 1. AFFICHER   ###");
        System.out.println("#### 2. AJOUTER    ###");
        System.out.println("#### 3. MODIFIER   ###");
        System.out.println("#### 4. SUPPRIMER  ###");
        System.out.println("#### 5. RETOUR     ###");
        System.out.println("######################");
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
                        menuAfficherConsultation();
                        break;
            }
            case 2 -> {  
                        ajouterConsultation();
                        break;
            }
            case 3 -> {
                        menuModifierConsultation();
                        break;
            }
            case 4 ->{
                        supprimerConsultation();
                        break;
            }
            case 5 ->{
                        Hopital.menuPrincipal();
                        break;
            }
            default -> throw new AssertionError();
            }
    }
    
    public void menuAfficherConsultation()
    {
        System.out.println("#####################################");
        System.out.println("####    AFFICHER CONSULTATION     ###");
        System.out.println("#####################################");
        System.out.println("#### 1. TOUTES LES CONSULTATIONS  ###");
        System.out.println("#### 2. CONSULTATION PAR MEDECINS ###");
        System.out.println("#### 3. DIAGNOSTICS FREQUENTS     ###");
        System.out.println("#### 4. RETOUR                    ###");
        System.out.println("#####################################");
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
            case 1 ->{
                        afficherConsultation();
                        break;
            }
            case 2 -> {  
                        afficherNbConsultParMedecin();
                        break;
            }
            case 3 -> {
                        afficherDiagnostic();
                        break;
            }
            case 4 -> {
                        menuConsultation();
                        break;
            }
            default -> throw new AssertionError();
        }       
    }
     
    public void menuModifierConsultation()
    {
        System.out.println("########################");
        System.out.println("####     MODIFIER    ###");
        System.out.println("########################");
        System.out.println("####  1. COUT        ###");
        System.out.println("####  2. DIAGNOSTIC  ###");
        System.out.println("####  3. RETOUR      ###");
        System.out.println("########################");
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
                        modifierCoutConsult();
                        break;
            }
            case 2 -> {  
                        modifierDiagnosticConsult();
                        break;
            }
            case 3 -> {
                        menuConsultation();
                        break;
            }
            default -> throw new AssertionError();
        }       
    }
    
    /*
        Suppression d'une Consultation donnée
        A noter que cette suppression n'est possible que si cette consultation ne fait pas l'objet d'une Hospitalisation
    */  
    public void supprimerConsultation()        //Suppresion d'une consultation donnée
    {
        try 
        {            
            int numero;
            scan = new Scanner(System.in);
            
            System.out.println("Quel est le numéro de la Consultation à supprimer ?");
            numero = scan.nextInt();
            
            requette = "DELETE FROM Consultation WHERE numConsult = "+numero+"";
            statement = connecter.connecterBD().createStatement();
            statement.executeUpdate(requette);
            connecter.connecterBD().close();
            
            System.out.println("La Consultation "+numero+" a été supprimée !");
            System.out.println("");
            System.out.println("");
            
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuConsultation();
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }
    }    
}
