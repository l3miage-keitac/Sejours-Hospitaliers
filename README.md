# Gestion des Séjours hospitaliers
#### Université Grenoble Alpes - Niveau L2 Informatique et MIAGE

Ce projet implémente une application de gestion des hospitalisations à l'hôpital. 

Le système permet de gérer les patients, les médecins, les consultations, les hospitalisations, les services, les chambres et les maladies complexes.

L'application est développée en Java.

# Fonctionnalités :

- Gérer les patients : ajout, suppression et affichage de patients.
- Gérer les médecins : ajout, suppression, affichage et modification de médecins.
- Gérer les consultations : ajout, suppression, affichage et modification de consultations.
- Gérer les hospitalisations : ajout, suppression, affichage et modification d'hospitalisations.
- Gérer les maladies complexes : affichage, suppression et modification de diagnostics.
- Gérer les services : ajout, suppression et affichage de services.
- Gérer les chambres : ajout, suppression, affichage et disponibilité de chambres.

## Services rendus par la Base :

-	Quel est le coût de l’hospitalisation d’un patient ?
-	Quels sont les diagnostics fréquents ?
-	Une chambre est-elle libre pour une période donnée ?
-	Nombre de Chambres par service ?
-	Nombre Médecins par service ?
-	Combien de Consultations effectué par Médecins ?

## Classes :

Le projet est composé de plusieurs classes, chacune correspondant à un objet du domaine :

- Connexion : permettant la connexion à la Base de données.
- CreationTable : qui crée les tables de la Base de données.
- Service : représentant un service de l'hôpital.
- Chambre : représentant une chambre de l'hôpital.
- Medecin : représentant un médecin de l'hôpital.
- Patient : représentant un patient de l'hôpital.
- Consultation : représentant une consultation médicale.
- Hospitalisation : représentant une hospitalisation.
- Maladiescomplexes : représentant une consultation complexe.


## Utilisation :

Pour utiliser l'application, vous devez exécuter la classe principale du projet, qui est « Hopital.java ». Cette classe contient une méthode main qui lance le programme et vous permet d'interagir avec les différentes fonctionnalités du système.

## Remarque :

Le projet a été conçu pour une utilisation en ligne de commande et ne dispose pas d'interface graphique. Il utilise une base de données en mémoire pour stocker les données des différents objets du domaine. 

#### Auteur :

Ce projet a été développé par KEITA Ciré dans le cadre de ma formation universitaire.
![image](https://github.com/l3miage-keitac/Sejours-Hospitaliers/assets/156134844/149c3330-d551-41c2-a0b7-b58cf7084001)
