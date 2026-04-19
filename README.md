Ce projet consiste en le développement d'une application de bureau en Java utilisant l'interface graphique JavaFX. Il s'inscrit dans le cadre du TP-projet 2025-2026 de la Licence Pro DevOps à l'Université Claude Bernard Lyon 1.


L'application permet à deux joueurs de s'affronter via une architecture Client-Serveur robuste utilisant le protocole TCP.

Fonctionnalités Clés
Mode 2 joueurs : Synchronisation en temps réel des coups via Sockets TCP.

Interface Moderne : Design inspiré de Chess.com (Thème Vert/Crème).

Interaction Intuitive : Support complet du Drag & Drop (Glisser-Déposer) pour déplacer les pièces avec affichage des coups possibles.

Gestion ds règles (GameManager) :

Validation des mouvements spécifiques à chaque pièce.

Gestion des collisions et obstacles.

Détection automatique de l'Échec et Mat.

Adaptation de Vue : Inversion automatique du plateau pour le joueur jouant les Noirs.

Coordonnées : Affichage des lignes (1-8) et colonnes (a-h) pour une meilleure lisibilité.

Architecture Technique
Le projet suit une architecture MVC (Modèle-Vue-Contrôleur) afin de séparer la logique métier de l'affichage :

Le Modèle (GameManager.java) : Le "cerveau" de l'application. Il gère l'état du plateau logique, simule les coups pour vérifier la mise en échec et valide les règles.

La Vue (chess-view.fxml & ChessController.java) : Gère l'affichage graphique, les animations de Drag & Drop et les interactions utilisateur.

La Communication (ChessClient.java & ChessServer.java) : Gère l'échange de messages protocolaires  pour synchroniser les instances.

 Structure du Projet
Plaintext
src/main/
├── java
│   ├── Communication        # Logique réseau (Sockets, Handlers, Messages)
│   │   ├── ChessClient.java
│   │   ├── ChessServer.java
│   │   ├── Message.java
│   │   └── PlayerHandler.java
│   ├── Pieces               # Modèles de données et règles par pièce
│   │   ├── Piece.java (Abstract)
│   │   ├── Pawn.java, Rook.java, Knight.java...
│   └── org.devops.chess_javafx
│       ├── ChessController.java # Gestionnaire de l'interface et du Drag & Drop
│       ├── GameManager.java     # Arbitre et logique des règles d'échecs
│       ├── HelloApplication.java
│       └── Launcher.java        # Point d'entrée pour éviter les erreurs de modules
└── resources
    ├── Assets               # Ressources graphiques (Sprites des pièces)
    └── org.devops.chess_javafx
        ├── chess-view.fxml      # Layout de l'échiquier
        └── hello-view.fxml      # Menu d'accueil
Installation et Lancement
Prérequis
Java 17 ou supérieur.

Maven pour la gestion des dépendances JavaFX.

Lancement
Démarrer le Serveur :
Exécutez la classe ChessServer pour ouvrir le port d'écoute (par défaut 8080).

Lancer les Clients :
Lancez deux instances de Launcher.

Connexion :
Entrez l'adresse IP du serveur (utilisez 127.0.0.1 pour un test local). Le serveur attribuera aléatoirement les couleurs et la partie commencera dès que deux joueurs seront connectés.



Règles du Jeu d'Échecs

Les échecs sont un jeu de stratégie opposant deux joueurs sur un plateau de 64 cases en 8x8. L'objectif est de mettre le Roi adverse en Échec et Mat.

Les Pièces et leurs déplacements
Le Roi : Se déplace d'une case dans toutes les directions. Sa perte signifie la fin de la partie.

La Dame : La pièce la plus mobile ; elle se déplace de n'importe quel nombre de cases en ligne droite ou en diagonale.

La Tour : Se déplace horizontalement ou verticalement.

Le Fou : Se déplace uniquement en diagonale.

Le Cavalier : Se déplace en "L" et peut sauter par-dessus les autres pièces.

Le Pion : Avance d'une case (ou deux au départ) et capture en diagonale.

Situations de fin de partie
Échec : Le Roi est menacé. Le joueur doit obligatoirement répondre à cette menace.

Échec et Mat : Le Roi est menacé et aucune parade n'est possible. La partie est terminée.

Pat : Match nul où le joueur n'a plus de coups légaux mais son Roi n'est pas en échec.

Abandon : Un bouton permet de mettre fin à la partie, déclenchant une victoire par forfait pour l'adversaire.

Auteurs : Sidney TRUCHET et Quentin PORTE (LP DevOps)
Date : Avril 2026

