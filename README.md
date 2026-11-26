# 👾 Galaga — jeu d'arcade en Java

Remake du jeu d'arcade **Galaga** en Java, réalisé en binôme dans le cadre du cours de programmation orientée objet (L2 Informatique, Université de Rennes — ISTIC).

## ✨ Fonctionnalités
- **Sprites personnalisés** : images pixelisées chargées depuis des fichiers texte `.spr` (un caractère = une couleur, avec transparence).
- **Formation ennemie** : les ennemis se déplacent en groupe, rebondissent sur les bords et lancent des attaques ou des tirs selon des probabilités.
- **Trois types d'ennemis** :
  - *Bee* : descentes en zigzag
  - *Butterfly* : attaque en diagonale
  - *Moth* : intercepteur qui rapporte plus de points
- **Système de tirs** : projectiles alliés et ennemis gérés par un gestionnaire dédié (3 tirs simultanés maximum).
- **Score et meilleur score** : sauvegarde automatique du highscore, scores flottants à la destruction d'un ennemi.
- **Niveaux** : chargés depuis des fichiers `.lvl`, passage automatique au niveau suivant.
- **HUD** : vies, niveau, score, écran d'introduction et Game Over.

## 🏗️ Conception
Le projet a été conçu en **UML** avant d'être codé (voir `diagramme-classes.puml`) :
- une classe abstraite `Entite` dont héritent le vaisseau du joueur et les ennemis ;
- un état d'ennemi géré par une énumération (`EtatEnnemi`) ;
- des responsabilités séparées : `Collision`, `GestionnaireTirs`, `ScoreManager`, `LevelLoader`, `Formation`.

## ▶️ Lancer le jeu
```bash
cd src
javac -d ../out engine/*.java game/*.java game/actors/*.java
cd ..
java -cp out engine.App
```
Le dossier `ressources/` doit se trouver à la racine, d'où le jeu est lancé.

**Commandes :** ← → pour se déplacer, Espace pour tirer ou recommencer après un Game Over.

## 🛠️ Technologies
Java · POO (héritage, classes abstraites, énumérations) · UML · StdDraw

## 👥 Auteurs
Hadi Beddy et Tarek Bounouni
