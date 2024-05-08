INSERT INTO Service(numService, libelle)
VALUES (1,'Cardiologie'),
       (2, 'Chirurgie'),
       (3,'Gastro-entérologie');

INSERT INTO Chambre(numCham,nomType, prix, numService)
VALUES (1,'Individuelle', 40, 1),
       (2, 'Individuelle', 40, 1),
	   (25,'Double', 70, 12);

	  
INSERT INTO Patient(numPatient, nomP, prenomP, dateNaiss)
VALUES (1, 'DUPONT', 'Jeanne', '1955-07-11'),
       (2, 'MARTIN', 'Pierre', '1972-02-15');
	 	 
INSERT INTO Medecin(numMedecin, nomMed, prenomMed, specialite, numService)
VALUES (1,'DUPONT', 'Sophie', 'Cardiologue', 1),
       (2,'ROUSSEL', 'Alexandre', 'Cardiologue', 1);  

INSERT INTO Consultation (numPatient, numMedecin, type, diagnostic, dateConsult, coutConsult)
VALUES 
(1, 11, 'Normal', 'Grippe', '2022-04-05', 50.00);

INSERT INTO Hospitalisation (numConsult, numCham,  dateEntree, dateSortie) VALUES
(12, 8, '2023-04-28', '2023-05-06'),
(17, 27, '2023-04-02', '2023-04-04');

	   
