# Guide SonarQube pour KFLOW

Ce guide explique comment configurer et utiliser SonarQube pour analyser la qualité du code du projet KFLOW.

## Table des matières

- [Introduction](#introduction)
- [Installation de SonarQube](#installation-de-sonarqube)
- [Configuration du projet](#configuration-du-projet)
- [Exécution de l'analyse](#exécution-de-lanalyse)
- [Interprétation des résultats](#interprétation-des-résultats)
- [Intégration CI/CD](#intégration-cicd)

---

## Introduction

SonarQube est une plateforme open-source pour l'analyse continue de la qualité du code. Elle détecte :
- 🐛 **Bugs** : Erreurs potentielles dans le code
- 🔒 **Vulnérabilités de sécurité** : Failles de sécurité
- 💡 **Code Smells** : Problèmes de maintenabilité
- 📊 **Couverture de code** : Pourcentage de code testé
- 📋 **Duplications** : Code dupliqué

Pour KFLOW, SonarQube analyse :
- **Backend** : Code Java (Spring Boot) avec couverture JaCoCo
- **Frontend** : Code TypeScript/Angular avec couverture Karma

---

## Installation de SonarQube

### Option 1 : Docker (Recommandé)

La méthode la plus simple pour démarrer SonarQube localement :

```bash
# Démarrer SonarQube avec Docker Compose
docker-compose -f docker-compose.sonar.yml up -d

# Vérifier que les conteneurs sont démarrés
docker-compose -f docker-compose.sonar.yml ps

# Voir les logs
docker-compose -f docker-compose.sonar.yml logs -f sonarqube
```

**Premier démarrage :**
- Attendre 2-3 minutes que SonarQube démarre complètement
- Accéder à http://localhost:9000
- Identifiants par défaut : `admin` / `admin`
- **Important** : Changer le mot de passe lors de la première connexion

**Arrêter SonarQube :**
```bash
docker-compose -f docker-compose.sonar.yml down
```

**Nettoyer les données (réinitialisation complète) :**
```bash
docker-compose -f docker-compose.sonar.yml down -v
```

### Option 2 : Installation manuelle

**Prérequis :**
- Java 17 ou supérieur
- PostgreSQL (ou H2 pour développement)

1. Télécharger SonarQube : https://www.sonarsource.com/products/sonarqube/downloads/
2. Extraire l'archive
3. Éditer `conf/sonar.properties` pour la base de données
4. Lancer : `bin/[OS]/sonar.sh start` (Linux/Mac) ou `bin/windows-x86-64/StartSonar.bat` (Windows)

---

## Configuration du projet

### 1. Générer un token d'authentification

1. Se connecter à SonarQube (http://localhost:9000)
2. Aller dans **My Account** > **Security**
3. Générer un token avec le nom "KFLOW Analysis"
4. **Copier le token** (il ne sera plus visible ensuite)

### 2. Configurer les variables d'environnement

**Linux/Mac :**
```bash
export SONAR_TOKEN=your_sonarqube_token_here
export SONAR_HOST_URL=http://localhost:9000
```

**Windows (CMD) :**
```cmd
set SONAR_TOKEN=your_sonarqube_token_here
set SONAR_HOST_URL=http://localhost:9000
```

**Windows (PowerShell) :**
```powershell
$env:SONAR_TOKEN="your_sonarqube_token_here"
$env:SONAR_HOST_URL="http://localhost:9000"
```

### 3. Fichiers de configuration créés

Le projet contient déjà les fichiers de configuration nécessaires :

#### `sonar-project.properties`
Configuration globale du projet avec :
- Informations du projet (nom, clé, version)
- Sources à analyser (backend + frontend)
- Exclusions (node_modules, tests, fichiers générés)
- Configuration de la couverture de code

#### `kflow_back/pom.xml`
Plugins Maven ajoutés :
- **JaCoCo** : Couverture de code Java (version 0.8.12)
  - Génère les rapports dans `target/site/jacoco/`
  - Seuil minimum : 50% de couverture
- **Sonar Maven Plugin** : Scanner SonarQube (version 4.0.0.4121)

#### `kflow_front/angular.json`
Configuration de test avec couverture activée :
- `codeCoverage: true`
- Rapports dans `coverage/`
- Format LCOV pour SonarQube

---

## Exécution de l'analyse

### Méthode automatique (Recommandé)

Utiliser les scripts fournis qui gèrent tout automatiquement :

**Linux/Mac :**
```bash
./analyze-sonar.sh
```

**Windows :**
```cmd
analyze-sonar.bat
```

Le script exécute automatiquement :
1. ✅ Vérification que SonarQube est accessible
2. ✅ Compilation et tests backend avec JaCoCo
3. ✅ Tests frontend avec couverture Karma
4. ✅ Analyse SonarQube complète
5. ✅ Affichage du lien vers le dashboard

### Méthode manuelle

#### Étape 1 : Tests backend avec couverture

```bash
cd kflow_back
./mvnw clean test jacoco:report
```

Rapports générés :
- HTML : `target/site/jacoco/index.html`
- XML : `target/site/jacoco/jacoco.xml`

#### Étape 2 : Tests frontend avec couverture

```bash
cd kflow_front
npm run test -- --watch=false --code-coverage --browsers=ChromeHeadless
```

Rapports générés :
- HTML : `coverage/index.html`
- LCOV : `coverage/lcov.info`

#### Étape 3 : Analyse SonarQube

```bash
cd kflow_back
./mvnw sonar:sonar \
  -Dsonar.host.url=$SONAR_HOST_URL \
  -Dsonar.token=$SONAR_TOKEN \
  -Dsonar.projectBaseDir=..
```

---

## Interprétation des résultats

### Dashboard SonarQube

Accéder aux résultats : http://localhost:9000/dashboard?id=kflow

#### Métriques principales

**Reliability (Fiabilité)**
- **Bugs** : Erreurs dans le code qui peuvent causer des dysfonctionnements
- Objectif : 0 bugs

**Security (Sécurité)**
- **Vulnerabilities** : Failles de sécurité potentielles
- **Security Hotspots** : Code sensible nécessitant une revue
- Objectif : 0 vulnérabilités

**Maintainability (Maintenabilité)**
- **Code Smells** : Problèmes de qualité du code
- **Technical Debt** : Temps estimé pour corriger tous les problèmes
- Objectif : Ratio A (< 5% de dette)

**Coverage (Couverture)**
- **Code Coverage** : Pourcentage de code couvert par les tests
- Objectif backend : > 50% (configurable dans pom.xml)
- Objectif frontend : > 50%

**Duplications**
- **Duplicated Code** : Code dupliqué
- Objectif : < 3%

#### Grades de qualité

- **A** : Excellent (0-5%)
- **B** : Bon (6-10%)
- **C** : Moyen (11-20%)
- **D** : Faible (21-50%)
- **E** : Très faible (> 50%)

### Consulter les détails

1. **Issues** : Liste de tous les problèmes détectés
   - Filtrer par sévérité : Blocker, Critical, Major, Minor, Info
   - Filtrer par type : Bug, Vulnerability, Code Smell
   - Assigner à un développeur pour correction

2. **Measures** : Métriques détaillées
   - Voir l'évolution dans le temps
   - Comparer les versions

3. **Code** : Navigation dans le code source
   - Voir les problèmes ligne par ligne
   - Voir la couverture de code par fichier

---

## Configuration avancée

### Personnaliser les seuils de qualité

Éditer `sonar-project.properties` :

```properties
# Seuil de couverture minimum
sonar.coverage.jacoco.minimumRatio=0.60  # 60%

# Exclure certains fichiers de l'analyse
sonar.exclusions=**/generated/**,**/vendor/**

# Désactiver une règle spécifique
sonar.issue.ignore.multicriteria=e1
sonar.issue.ignore.multicriteria.e1.ruleKey=java:S1192
sonar.issue.ignore.multicriteria.e1.resourceKey=**/*.java
```

### Créer un Quality Gate personnalisé

1. Dans SonarQube : **Quality Gates** > **Create**
2. Ajouter des conditions :
   - Coverage > 60%
   - Duplicated Lines < 3%
   - Bugs = 0
   - Vulnerabilities = 0
3. Assigner au projet KFLOW

---

## Intégration CI/CD

### GitHub Actions

Créer `.github/workflows/sonarqube.yml` :

```yaml
name: SonarQube Analysis

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main, develop]

jobs:
  sonarqube:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v4
      with:
        fetch-depth: 0  # Analyse complète

    - name: Set up JDK 21
      uses: actions/setup-java@v4
      with:
        java-version: '21'
        distribution: 'temurin'

    - name: Set up Node.js
      uses: actions/setup-node@v4
      with:
        node-version: '21'

    - name: Run tests and SonarQube
      env:
        SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        SONAR_HOST_URL: ${{ secrets.SONAR_HOST_URL }}
      run: ./analyze-sonar.sh
```

**Configuration des secrets GitHub :**
1. Aller dans **Settings** > **Secrets and variables** > **Actions**
2. Ajouter `SONAR_TOKEN` et `SONAR_HOST_URL`

### GitLab CI

Créer `.gitlab-ci.yml` :

```yaml
sonarqube:
  image: maven:3.9-eclipse-temurin-21
  stage: test
  script:
    - ./analyze-sonar.sh
  variables:
    SONAR_TOKEN: $SONAR_TOKEN
    SONAR_HOST_URL: $SONAR_HOST_URL
  only:
    - main
    - develop
    - merge_requests
```

---

## Bonnes pratiques

### 1. Analyser régulièrement
- Avant chaque commit important
- Avant chaque pull request
- Dans le pipeline CI/CD

### 2. Corriger les problèmes par priorité
1. **Blocker & Critical** : À corriger immédiatement
2. **Major** : À corriger avant la mise en production
3. **Minor & Info** : À planifier

### 3. Maintenir une bonne couverture
- Écrire des tests pour le nouveau code
- Objectif : > 80% de couverture sur le nouveau code

### 4. Éviter le code dupliqué
- Extraire en fonctions/méthodes réutilisables
- Utiliser des patterns (Factory, Strategy, etc.)

### 5. Suivre l'évolution
- Consulter le dashboard régulièrement
- Comparer avec les versions précédentes
- Célébrer les améliorations ! 🎉

---

## Dépannage

### Problème : "SonarQube is not accessible"

**Solution :**
```bash
# Vérifier que les conteneurs sont démarrés
docker-compose -f docker-compose.sonar.yml ps

# Voir les logs
docker-compose -f docker-compose.sonar.yml logs sonarqube

# Redémarrer si nécessaire
docker-compose -f docker-compose.sonar.yml restart sonarqube
```

### Problème : "Insufficient privileges"

**Solution :**
- Vérifier que le token est correct
- Régénérer un token avec les permissions "Execute Analysis"

### Problème : Tests frontend échouent

**Solution :**
```bash
# Installer Chrome pour Karma
# Linux
sudo apt-get install chromium-browser

# Mac
brew install --cask google-chrome

# Windows : installer manuellement depuis https://www.google.com/chrome/
```

### Problème : Mémoire insuffisante pour SonarQube

**Solution :**
Éditer `docker-compose.sonar.yml` :
```yaml
sonarqube:
  environment:
    - SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true
  deploy:
    resources:
      limits:
        memory: 4G
```

---

## Ressources utiles

- [Documentation SonarQube](https://docs.sonarsource.com/sonarqube/latest/)
- [SonarQube Java rules](https://rules.sonarsource.com/java/)
- [SonarQube TypeScript rules](https://rules.sonarsource.com/typescript/)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
- [Karma Coverage](https://karma-runner.github.io/latest/config/coverage.html)

---

## Support

Pour toute question sur la configuration SonarQube du projet KFLOW, veuillez créer une issue sur le dépôt du projet.
