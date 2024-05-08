INSERT INTO Chambre(nomType, prix, numService)
VALUES ('Individuelle', 40, 50),   /* ces numeros de Service n'existent pas*/
       ( 'Individuelle', 40, 43);
	   
INSERT INTO Patient(numPatient, nomP, prenomP, dateNaiss)
VALUES (1, 'KEITA', 'Lady', '1953-07-11');  /*Va poser problème parce qu'il ya deja un patient numero 1*/

INSERT INTO Medecin(nomMed, prenomMed, specialite, numService)
VALUES ('DUPONT', 'Sophie', 'Cardiologue', 89),
       ('ROUSSEL', 'Alexandre', 'Cardiologue', 25);  /*Pas de bons numero de Service*/
	   
INSERT INTO MaladiesComplexes (numConsult, numMedecin, diagnostic, numMedecinAssistant1, numMedecinAssistant2) 
VALUES (291,1, 'Cancer du col', 3, 9);   /* Ce numero de Consultation n'existe pas*/
