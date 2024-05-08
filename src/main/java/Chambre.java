
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
public class Chambre {
    
    Connexion connecter;
    String requette;
    Statement statement;
    ResultSet resultSet;
    PreparedStatement pStatement;
    Scanner scan;
    String touche;
        
    public Chambre(){
        
        connecter = new Connexion();
    }
    /*
    Insertion à la fin de la table Chambre tout en respectant les contraintes
    
    Etat final: on a Table Chambre+1
     */
    public void ajouterChambre()              
    {
        try 
        {
            scan = new Scanner(System.in);  
            String type;
            float prix;
            int numService;
            
            do
            {
                System.out.println("Quel est le type de la chambre à ajouter ? (Individuelle ou Double ?)");
                type = scan.next();
            } while(!type.equals("Individuelle") & !type.equals("Double"));
            
            if(type.contains("ndividuel"))
            {
                prix = 40;    //40 euros par défaut pour les chambres Individuelles
            }
            else
            {
                prix = 70;    //70 euros par défaut pour les chambres Doubles
            }
            
            System.out.println("Quel est le numéro du Service dans lequel se trouve la chambre ?");
            numService = scan.nextInt();
                    
            requette = "INSERT INTO Chambre(nomType, prix, numService) VALUES ('"+type+"',"+prix+","+numService+")";
            pStatement = connecter.connecterBD().prepareStatement(requette);
            pStatement.executeUpdate();
            connecter.connecterBD().close();
            
            System.out.println("");
            System.out.println("Les données ont été insérées avec succès dans la table !");
            
            System.out.println("");
            System.out.println("");           
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuChambre();
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }       
    }
    /*
        Affichage de toutes les Chambres
    */     
    public void afficherChambre()             // Affichage de toutes les chambres de la table
    {
        try 
        {
            statement = connecter.connecterBD().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Chambre;");
            System.out.println("LA LISTE DES CHAMBRES");
            System.out.println("");
            System.out.print("Identifiant" +"\t");
            System.out.print("Prix" +"\t");
            System.out.print("numService" +"\t");
            System.out.println("Type" );            
            System.out.println("");
            
            while(resultSet.next())
            {
                System.out.print("    " +resultSet.getInt("numCham") +"\t"+"\t");
                System.out.print(resultSet.getFloat("prix")+"\t");
                System.out.print("     "+resultSet.getInt("numService")+"\t"+"\t");
                System.out.print(resultSet.getString("nomType"));                
                System.out.println("");
                System.out.println("");                
            }
            connecter.connecterBD().close();
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuAfficherChambre();
            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }        
    }

    /*
        Question posée : Une chambre est-elle libre pour une période donnée?
    */     
    public void afficherChambreLibre()        
    {
        try {
            LocalDate dateEntree;
            LocalDate dateSortie;

            System.out.println("");
            System.out.println("Veuillez saisir la date d'Entrée sous cette forme : AAAA-MM-JJ");
            dateEntree = LocalDate.parse(scan.next());
            
            System.out.println("Veuillez saisir la date de Sortie sous cette forme : AAAA-MM-JJ");
            dateSortie = LocalDate.parse(scan.next());   


            requette = "SELECT  numCham FROM Chambre WHERE numCham EXCEPT SELECT numCham FROM Hospitalisation WHERE ('"+dateEntree+"' <=dateEntree AND '"+dateSortie+"' >= dateEntree) OR ('"+dateEntree+"' >=dateSortie AND '"+dateSortie+"' <=dateSortie);";
            statement = connecter.connecterBD().createStatement();
            resultSet = statement.executeQuery(requette);
            System.out.println("");
            System.out.println("LA LISTE DES CHAMBRES LIBRES POUR LA PERIODE DU "+dateEntree+" AU "+dateSortie+"");
            System.out.println("");
            System.out.println("IdentifiantChambre");
            System.out.println("");
            
            while (resultSet.next()) 
            {  
                System.out.print("\t"+resultSet.getInt("numCham"));
                System.out.println("");
            }
            connecter.connecterBD().close();
            System.out.println("");
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuAfficherChambre();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } 
    }
    
    /*
        Question posée : Combien de chambre par service ?
    */  
    public void afficherChambreParService()   
    {
        try{    
            requette = "SELECT libelle, COUNT(numCham) AS Total FROM Service JOIN Chambre USING(numService) GROUP BY libelle ORDER BY Total DESC;";
            
            statement = connecter.connecterBD().createStatement();
            resultSet = statement.executeQuery(requette);
            System.out.println("LA LISTE DU NOMBRE DE CHAMBRES PAR SERVICE");
            System.out.println("");
            System.out.print("NombreChambre" +"\t");
            System.out.print("Service");
            System.out.println("");
            System.out.println("");
            
            while(resultSet.next()){
                System.out.print("    "+resultSet.getInt("Total") +"\t"+"\t");
                System.out.print(resultSet.getString("libelle"));
                System.out.println("");
                System.out.println("");
            }
            connecter.connecterBD().close();
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuAfficherChambre();            
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }
    }

    /*
        Modification du prix des Chambres en fonction du Type (Individuelle ou Double)
        Les prix par défaut sont: - 70euros pour les Chambres doubles 
                                  - 40euros pour les chambres individuelles
    */      
    public void modifierChambre()             
    {
        try 
        {
            scan = new Scanner(System.in);
            String type;
            float prix;
            
            do
            {
                System.out.println("Quel est le Type des Chambres à modifier ? (Individuelle ou Double ?)");
                type = scan.next();
            } while(!type.equals("Individuelle") & !type.equals("Double"));
            
            System.out.println("Quel est le prix de ces Chambres ?");
            prix = scan.nextFloat();
            
            requette = "UPDATE Chambre SET prix = '"+prix+"' WHERE nomType = '"+type+"';";
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
            menuChambre();
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }
    }
    
    public void menuChambre()
    {
        System.out.println("######################");
        System.out.println("####    CHAMBRE    ###");
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
                        menuAfficherChambre();
                        break;
            }
            case 2 -> {  
                        ajouterChambre();
                        break;
            }
            case 3 -> {
                        modifierChambre();
                        break;
            }
            case 4 -> {
                        supprimerChambre();
                        break;
            }
            case 5 ->{
                        Hopital.menuPrincipal();
                        break;
            }
            default -> throw new AssertionError();
            }
    }
    
    public void menuAfficherChambre()
    {
        System.out.println("################################");
        System.out.println("####     AFFICHER CHAMBRE    ###");
        System.out.println("################################");
        System.out.println("#### 1. Toutes les Chambres  ###");
        System.out.println("#### 2. Chambres Libres      ###");
        System.out.println("#### 3. Chambres par Service ###");
        System.out.println("#### 4. RETOUR               ###");
        System.out.println("################################");
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
                        afficherChambre();
                        break;
            }
            case 2 -> {  
                        afficherChambreLibre();
                        break;
            }
            case 3 -> {
                        afficherChambreParService();
                        break;
            }
            case 4 ->{
                        menuChambre();
                        break;
            }
            default -> throw new AssertionError();
        }       
    }

    /*
        Suppression d'une Chambre donnée
        A noter que cette suppression n'est possible que si cette chambre n'est pas utilisée dans une Hospitalisation
    */    
    public void supprimerChambre()            
    {
        try {
            
            int numero;
            scan = new Scanner(System.in);
            
            System.out.println("Quel est le numéro de la Chambre à supprimer ?");
            numero = scan.nextInt();
            
            requette = "DELETE FROM Chambre WHERE numCham = "+numero+"";
            statement = connecter.connecterBD().createStatement();
            statement.executeUpdate(requette);
            connecter.connecterBD().close();
            
            System.out.println("La Chambre "+numero+" a été supprimée !");
            
            System.out.println("");
            System.out.println("");
            
            System.out.println("Tapez une touche pour revenir en arrière");
            
            scan = new Scanner(System.in);
            touche = scan.next();
            menuChambre();
                       
        } catch (SQLException e) 
        {
            System.err.println(e.getMessage());
        }
    }    
}
