# Rapport : Développement d'un jeu de Chasse au Trésor VERSION CONSOLE

Par Nathanaël Houn, TP2A, Licence 2 Informatique à l'UFR-ST de Besançon

[TOC]

NOTE À Mme GREFFIER : J'ai fait tous les commentaires en anglais. Si vous les souhaitez en français pour la version finale graphique, merci de me le dire via Discord / mail et je changerai tout ça pour votre meilleure compréhension 
Cordialement, 
Nathanaël Houn



## Présentation de l'application

L'application à réaliser est un jeu de chasse au Trésor dans laquelle des personnages autonomes se déplacent sur le terrain pour accéder à un trésor.

## Conception de l'application

![Treasure_chase_classes](/mnt/DATA/Documents/Fac/S4/POA/Treasure-chase/Rapport/Treasure_chase_classes.jpg)

Le diagramme de classe ci-dessus présente la structure utilisée pour le développement de la version console du jeu.



Diagramme de classes final, choix justifié de structures de données, éventuels algorithmes intéressants

## Développement de l'application

Captures d'écran pour les points intéressant du programme. Organisation du travail

Ce qui a été développé, testé, non implanté, abandonné (check des fonctionnalités implantées)

Bilan technique sur les outils utilisés, difficultés de programmation rencontrées

### Changements sur le diagramme de classe initial

- Model.Board a un attribut `treasure` qui permet d'accéder facilement au trésor
- Model.Board a un méthode `doRound()` qui appelle le `process()` pour chaque Model.Hunter
- Model.Direction voit tous ses attributs renommés selon les points cardinaux pour une meilleure lisibilité
- Model.Direction gagne deux fonctions statiques utilitaires : `getRandom()` et `reverse()`
- Model.Hunter possède une case courante `currentCell` plutôt qu'une position courante : la position courante est accessible depuis la case courante, et ainsi on peut libérer la case courante depuis le Model.Hunter lors du `process` 
- Model.Hunter gagne une fonction `getWantedPosition()`
- Les attributs de Model.Cell sont maintenant en protected
- Model.Cell gagne un attribut `treasure` qui contient une référence vers le trésor du Model.Board
- Les classes héritées de Model.Cell ont été complètement réécrites, notamment pour contenir les directions à donner aux Model.Hunter qui sont calculées à l'initialisation du Model.Board plutôt qu'à l'exécution du programme


## Conclusion

Bilan par rapport au travail en monôme, par rapport à la formation

## Annexes

Autres captures d'écran, codes java intéressants



## À garder en tête pour la mise en forme

- Numéroter les pages
- Présentation aérée
- Figures titrées et légendées
- Présenter les points de conceptions importants de l'application
- Créer des cas pour lé démo, avec le fonctionnement général de l'application et différents cas particuliers
- Préparer la présentation