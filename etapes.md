# Atelier spring : projet kayak freestyle

## Création du projet

sur Spring initialzr, créer un projet (java, maven, ...) avec les dépendances:
- driver mysql
- spring-starter-data-jpa
- spring web
- spring dev tools
- lombok
- hibernate validator

Télécharger le projet et ouvrez-le dans vscode.

## Couche d'accès aux données

- créer les entités métier, avec les annotations
  - hibernate
  - lombok
  - jackson ?
- créer les repositories (un pour chaque entité métier)

## Couche métier

- créer les services (un pour chaque entité métier)
- pour chaque service, ajouter les méthodes :
  - `findAll`
  - `findById`
  - `save`
  - `update`
  - `deleteById`

## Couche de présentation

- créer les controllers (un pour chaque entité métier)
- pour chaque controllers, ajouter les méthodes :
  - `findAll`
  - `findById`
  - `save`
  - `update`
  - `deleteById`