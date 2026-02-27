Ce projet consiste en le développement d'une application de bureau en Java utilisant l'interface graphique JavaFX. Il s'inscrit dans le cadre du TP-projet 2025-2026 de la Licence Pro DevOps à l'Université Claude Bernard Lyon 1.

L'application repose sur une architecture client-serveur utilisant le protocole TCP  pour permettre des duels d'échecs en réseau.

📖 Règles du Jeu d'Échecs
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


