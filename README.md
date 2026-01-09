# Kflow  
*Application de gestion de compétitions de kayak freestyle*  
**Java 21 / Spring Boot 3.2.3 • Angular 17 • Docker**

---

## 🧭 Sommaire
1. [Description du projet](#description-du-projet)  
2. [Technologies utilisées](#technologies-utilisées)  
3. [Prérequis](#prérequis)  
4. [Installation avec Docker](#installation-avec-docker)  
5. [Arborescence du projet](#arborescence-du-projet)  
6. [Endpoints principaux](#endpoints-principaux)  
7. [Tests](#tests-automatisés)  
8. [Roadmap personnelle](#roadmap-personnelle)  
9. [Licence](#licence)

---

## 📌 Description du projet

**Kflow** est un projet personnel développé dans le cadre d’un apprentissage approfondi de **Spring Boot** et **Angular**.  
Il permet de gérer une compétition de kayak freestyle : création des épreuves, saisie des participants, consultation des résultats.

L’application respecte une architecture modulaire, sécurisée (JWT RSA) et est entièrement **conteneurisée via Docker**.

---

## ⚙️ Technologies utilisées

| Couche        | Outils / Frameworks                   | Versions       |
|---------------|----------------------------------------|----------------|
| **Back-end**  | Java 21 (OpenJDK), Spring Boot 3.2.3   | 21.0.7, 3.2.3  |
|               | Spring Security (JWT RSA)             | 6.2.x          |
|               | Spring Data JPA / Hibernate           | 6.4.x          |
| **Base de données** | MySQL (prod/dev) / H2 (tests)   | 8.0.x          |
| **Front-end** | Angular CLI + Angular Material        | 17.x           |
|               | TypeScript, Jasmine, Karma            | 5.x, ^5        |
| **Conteneurs**| Docker, Docker Compose                | latest         |

---

## ✅ Prérequis

- **Docker Desktop** installé et opérationnel  
- **Git** pour cloner le projet

---

## 🚀 Installation avec Docker

### 1. Cloner le projet

```bash
git clone https://github.com/chinokira/kflow.git
cd kflow
2. Lancer l’ensemble des services
bash
Copier
Modifier
docker compose up --build
👉 Cela va :

créer un conteneur MySQL (kflow-db),

démarrer le back-end Spring Boot (kflow-back),

lancer le front-end Angular (kflow-front, dispo sur http://localhost:4200)

3. Connexion par défaut
Email : admin@admin.admin

Mot de passe : adminadmin
ou
Email : user@user.user

Mot de passe : useruser

(Identifiants injectés par data-test.sql)

🗂 Arborescence du projet
css
Copier
Modifier
kflow/
 ├── docker-compose.yml
 ├── kflow_back/
 │   ├── src/main/java/kayak/…
 │   │    ├── controllers/
 │   │    ├── services/
 │   │    ├── repositories/
 │   │    └── models/          ← Entités JPA
 │   └── src/test/java/        ← Tests JUnit
 ├── kflow_front/
 │   ├── src/app/
 │   │    ├── components/
 │   │    ├── pages/
 │   │    ├── services/
 │   │    └── guards/          ← Auth & Admin Guards
 │   └── src/environments/
🔗 Endpoints principaux
Méthode	URL	Description	Accès
POST	/authenticate	Authentification	Public
GET	/competitions	Liste des compétitions	Public
GET	/competitions/{id}	Détail d'une compétition	Public
POST	/competitions	Créer une compétition	ADMIN
PUT	/competitions/{id}	Modifier une compétition	ADMIN

📌 Les appels protégés doivent contenir un header :
Authorization: Bearer <access-token>

🧪 Tests 
🖥 Back-end : JUnit
bash
Copier
Modifier
cd kflow_back
./mvnw test
# Résultat attendu : Tests run: 9, Failures: 0
🌐 Front-end : Karma + Jasmine
bash
Copier
Modifier
cd kflow_front
npm ci
npm run test -- --watch=false
# Résultat attendu : 11 specs, 0 failures
🛠 Roadmap personnelle
Sprint Optimisation : indexations SQL, profiling Hibernate

Sprint Qualité : JaCoCo pour la couverture + ESLint strict

Sprint CI/CD : pipeline GitHub Actions, image Docker multi-stage

Sprint Temps réel : ajout de WebSockets pour scores en direct

---

## 📊 Analyse de la qualité du code avec SonarQube

Le projet est configuré pour utiliser **SonarQube**, une plateforme d'analyse continue de la qualité du code qui détecte les bugs, vulnérabilités, code smells et mesure la couverture de code.

### Démarrage rapide

1. **Lancer SonarQube avec Docker :**
   ```bash
   docker-compose -f docker-compose.sonar.yml up -d
   ```

2. **Accéder à SonarQube :**
   - URL : http://localhost:9000
   - Identifiants par défaut : `admin` / `admin`
   - Changer le mot de passe lors de la première connexion

3. **Générer un token d'authentification :**
   - My Account > Security > Generate Tokens
   - Copier le token généré

4. **Lancer l'analyse :**
   ```bash
   # Linux/Mac
   export SONAR_TOKEN=your_token_here
   ./analyze-sonar.sh

   # Windows
   set SONAR_TOKEN=your_token_here
   analyze-sonar.bat
   ```

5. **Consulter les résultats :**
   - Dashboard : http://localhost:9000/dashboard?id=kflow

### Documentation complète

Pour plus de détails sur la configuration, l'interprétation des résultats et l'intégration CI/CD, consultez le guide complet : **[SONARQUBE.md](SONARQUBE.md)**

Le guide contient :
- Installation et configuration détaillées
- Explication des métriques (Bugs, Vulnérabilités, Code Smells, Couverture)
- Interprétation du dashboard
- Intégration dans GitHub Actions / GitLab CI
- Bonnes pratiques et dépannage

---

📄 Licence
Projet sous licence MIT – librement réutilisable et modifiable.
Développé dans un cadre personnel à des fins pédagogiques (certification CDA 2025).
