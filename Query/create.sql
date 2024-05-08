
CREATE TABLE IF NOT EXISTS Service (
  numService INTEGER PRIMARY KEY,
  libelle VARCHAR (50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Medecin (
  numMedecin INTEGER PRIMARY KEY , 
  nomMed VARCHAR (50),
  prenomMed VARCHAR (50),
  specialite VARCHAR(50),
  numService INTEGER NOT NULL,
  FOREIGN KEY (numService) REFERENCES Service(numService)
);

CREATE TABLE IF NOT EXISTS Patient (
  numPatient INTEGER PRIMARY KEY,
  nomP VARCHAR (50),
  prenomP VARCHAR (50),
  dateNaiss DATE
);

CREATE TABLE IF NOT EXISTS Chambre (
  numCham INTEGER PRIMARY KEY,
  nomType VARCHAR (50),
  prix REAL,
  numService INTEGER NOT NULL,
  FOREIGN KEY (numService) REFERENCES Service(numService),
  CONSTRAINT ck_chambre_c0 CHECK (prix > 0),
  CONSTRAINT ck_chambre_c1 CHECK (nomType = 'Individuelle' OR nomType = 'Double' )
);

CREATE TABLE IF NOT EXISTS Hospitalisation (
  numHospi INTEGER PRIMARY KEY,
  numConsult INTEGER NOT NULL,
  numCham INTEGER NOT NULL,
  dateEntree DATE NOT NULL,
  dateSortie DATE NOT NULL,
  CONSTRAINT fk_hospitalisation_c0 FOREIGN KEY (numConsult) REFERENCES Consultation(numConsult),
  CONSTRAINT fk_hospitalisation_c1 FOREIGN KEY (numCham) REFERENCES Chambre(numCham),
  CONSTRAINT ck_dureeHospitalisation CHECK (dateSortie > dateEntree)
);

CREATE TABLE IF NOT EXISTS Consultation (
  numConsult INTEGER PRIMARY KEY,
  numPatient INTEGER NOT NULL,
  numMedecin INTEGER NOT NULL,
  type VARCHAR (50) NOT NULL DEFAULT 'Normale',               /*  AJOUT DU TYPE POUR GERER LE CAS DES MALADIES COMPLEXES*/
  diagnostic VARCHAR (60) DEFAULT 'RAS',
  dateConsult DATE NOT NULL,
  coutConsult REAL NOT NULL,           
  CONSTRAINT fk_consultation_c0 FOREIGN KEY (numPatient) REFERENCES Patient(numPatient),
  CONSTRAINT fk_consultation_c1 FOREIGN KEY (numMedecin) REFERENCES Medecin(numMedecin),
  CONSTRAINT ck_consultation_c0 CHECK (coutConsult  > 0),
  CONSTRAINT ck_consultation_c1 CHECK (type = 'Normal' OR type = 'Complex')
);

CREATE TABLE IF NOT EXISTS MaladiesComplexes (
  numMaladieC INTEGER PRIMARY KEY,
  numConsult INTEGER NOT NULL,
  numMedecin INTEGER NOT NULL,
  diagnostic VARCHAR (60),
  numMedecinAssistant1 INTEGER NOT NULL,
  numMedecinAssistant2 INTEGER DEFAULT NULL,
  CONSTRAINT fk_maladiesComp_c0 FOREIGN KEY (numMedecin) REFERENCES Medecin(numMedecin),
  CONSTRAINT fk_maladiesComp_c1 FOREIGN KEY (numMedecinAssistant1) REFERENCES Medecin(numMedecin),
  CONSTRAINT fk_maladiesComp_c2 FOREIGN KEY (numMedecinAssistant2) REFERENCES Medecin(numMedecin),
  CONSTRAINT fk_maladiesComp_c3 FOREIGN KEY (numConsult) REFERENCES Consultation(numConsult),
  CONSTRAINT fk_maladiesComp_c4 FOREIGN KEY (diagnostic) REFERENCES Consultation(diagnostic)
);


