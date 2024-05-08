
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

/**
 *
 * @author cirekeita
 */
public class Hopital {
    
    static CreationTable create = new CreationTable();
    Connexion connecter;
    
    static Chambre chambre = new Chambre();           
    static Consultation consult = new Consultation();
    static Hospitalisation hospi = new Hospitalisation();
    static MaladieComplexe maladieC = new MaladieComplexe();
    static Patient patient = new Patient();
    static Medecin medecin = new Medecin();
    static Service service = new Service();

    public Hopital()
    {

    }   

    public static void main(String[] args) throws SQLException 
    {    
        Connexion connecter = new Connexion();
        try {
            create.executeQuery();
        } catch (IOException e) 
        {
            System.out.println("Table non créée" +e.getMessage());
        }
        menuPrincipal();                 
    }
    
    public static void menuPrincipal()
    {
        System.out.println("##############################");
        System.out.println("####  LA LISTE DES TABLES  ###");
        System.out.println("##############################");
        System.out.println("#### 1. CHAMBRE            ###");
        System.out.println("#### 2. CONSULTATION       ###");
        System.out.println("#### 3. HOSPITALISATION    ###");
        System.out.println("#### 4. MALADIES COMPLEXES ###");
        System.out.println("#### 5. MEDECIN            ###");
        System.out.println("#### 6. PATIENT            ###");
        System.out.println("#### 7. SERVICE            ###");
        System.out.println("#### 8. SORTIR             ###");
        System.out.println("##############################");
        System.out.println("");
        
        System.out.println("Quelle est la table à laquelle vous souhaitez accéder ? ");
        String reponse;
        char c;
        int choix;
        Scanner scan = new Scanner(System.in);
        do { 
            System.out.println("Veuillez saisir un entier entre 1 et 8 ! ");
                        
            reponse = scan.next();
            c = reponse.charAt(0);   //CHOIX NE PORTANT QUE SUR LE 1ER CARACTERE DANS LE CAS OÙ L'UTILISATEUR SAISI UNE CHAINE AU LIEU D'UN ENTIER
            choix = Character.getNumericValue(c);  //RECUPERE LA VALEUR ENTIERE DE CE 1ER CARACTERE
            
        } while (choix != 1 & choix != 2 & choix != 3 & choix != 4 & choix != 5 & choix != 6 & choix != 7 & choix != 8);
        
        switch (choix) 
        {
            case 1 ->   // LA TABLE CHAMBRE
            {
                        chambre.menuChambre(); 
                        menuPrincipal();
            }
            case 2 ->   // LA TABLE CONSULTATION
            {
                        consult.menuConsultation();
                        menuPrincipal();
            }
            
            case 3 ->   // LA TABLE HOSPITALISATION
            {
                        hospi.menuHospitalisation();
                        menuPrincipal();
            }

            case 4 ->   // LA TABLE MALADIES COMPLEXES
            {
                        maladieC.menuMaladieComplexe();
            }

            case 5 ->   // LA TABLE MEDECIN
            {
                        medecin.menuMedecin();
            }
            case 6 ->   // LA TABLE PATIENT
            {
                        patient.menuPatient();                                        
            }
            case 7 ->   // LA TABLE SERVICE
            {
                        service.menuService();
            }
            case 8 ->   // SORTIR DU PROGRAMME
            {
                        System.out.println("");
                        System.out.println("MERCI ET A TRES BIENTOT !");
                        System.out.println("");
                        System.exit(0);
            }
            default -> throw new AssertionError();
        }
    }
}
