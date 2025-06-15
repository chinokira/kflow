# Kflow  
_Gestion personnelle de compétitions de kayak freestyle – Java 21 / Spring Boot 3.2.3 & Angular 17_

---

## Sommaire
1. [Description du projet](#description-du-projet)  
2. [Empilement technologique](#empilement-technologique)  
3. [Prérequis](#prérequis)  
4. [Installation et lancement](#installation-et-lancement)  
5. [Arborescence du code](#arborescence-du-code)  
6. [Endpoints principaux](#endpoints-principaux)  
7. [Tests automatisés](#tests-automatisés)  
8. [Road-map personnelle](#road-map-personnelle)  
9. [Licence](#licence)

---

## Description du projet

Kflow est un **projet personnel d’apprentissage**.  
Objectif : disposer d’un petit outil web pour créer, modifier puis consulter des compétitions de kayak freestyle.  
Aucun objectif commercial ; le développement suit mon propre rythme d’apprentissage Java/Spring Boot et Angular.

---

## Empilement technologique

| Couche | Technologie | Version |
|--------|-------------|---------|
| Back-end | Java 21 (OpenJDK) | 21.0.7  
|  | Spring Boot | 3.2.3  
|  | Spring Security 6 (JWT RSA) | 6.2.x  
|  | Spring Data JPA / Hibernate 6 | 6.4.x  
|  | Base de données | MySQL 8 (dev) / H2 (tests) |
| Front-end | Angular | 17.x  
|  | TypeScript | 5.x  
|  | Angular Material | 17.x |
| Build & CI | Maven 3.9, npm 10 |  
|  | GitHub Actions (build + tests) |  

---

## Prérequis

| Outil | Version mini | Commentaire |
|-------|--------------|-------------|
| JDK   | **21** | `java -version`  
| Maven | ≥ 3.8        | Wrapper fourni (`./mvnw`) |
| Node  | ≥ 18         | (Angular 17 supporte 18+) |
| npm   | ≥ 10         | |
| MySQL | ≥ 8          | Ou laissez Spring démarrer H2 en mémoire |
| Git   | ≥ 2.30       | |

---

## Installation et lancement

### 1. Clone du dépôt

```bash
git clone https://github.com/chinokira/kflow.git
cd kflow
2. Lancer le back-end
bash
Copier
Modifier
cd kflow_back
./mvnw spring-boot:run
Le profil par défaut utilise H2 en mémoire (pratique pour tester).

Pour MySQL local, créez une base kflow, renseignez spring.datasource.* dans src/main/resources/application.properties puis relancez.

3. Lancer le front-end
bash
Copier
Modifier
cd ../kflow_front
npm ci        # installe les dépendances
ng serve      # application accessible sur http://localhost:4200
4. Se connecter
Login : admin@kflow.local

Mot de passe : admin123

(Ces identifiants sont insérés par le script data-test.sql lors du démarrage en profil dev.)

Arborescence du code (extrait)
css
Copier
Modifier
kflow/
 ├─ kflow_back/
 │   ├─ src/main/java
 │   │   └─ kayak/freestyle/competition/kflow
 │   │        ├─ controllers/
 │   │        ├─ services/
 │   │        ├─ repositories/
 │   │        └─ models/   ← Entités JPA (Annexe A)
 │   └─ src/test/java/…    ← 9 tests JUnit (Annexe B)
 └─ kflow_front/
     ├─ src/app
     │   ├─ components/
     │   ├─ pages/
     │   ├─ services/      ← authentication.service.ts, etc.
     │   └─ guards/        ← authGuard, adminGuard
     └─ src/environments/
Endpoints principaux
Méthode	URL	Description	Accès
POST	/authenticate	Renvoie access-token (60 s) + refresh-token (24 h)	Public
GET	/competitions	Liste paginée des compétitions	Public
POST	/competitions	Créer une compétition	ADMIN
PUT	/competitions/{id}	Mettre à jour	ADMIN
GET	/competitions/{id}	Détail + stages + catégories	Public

JWT à placer dans l’en-tête Authorization: Bearer <token>.

Tests automatisés
bash
Copier
Modifier
# Back-end
cd kflow_back
./mvnw test
# => Tests run: 9, Failures: 0

# Front-end
cd ../kflow_front
npm run test -- --watch=false
# => 11 specs, 0 failures
Road-map personnelle
 Sprint Performance : profiling Hibernate, index supplémentaires.

 Sprint Qualité : intégration JaCoCo et ESLint strict.

 Sprint CI/CD : Docker multi-stage + déploiement sur un VPS.

 Sprint Temps réel : notifications WebSocket (résultats live).

Licence
Projet sous licence MIT. Vous pouvez l’utiliser et le modifier librement pour vos propres expérimentations, à condition de conserver cette notice.

Kflow est développé sur mon temps libre pour apprendre et pratiquer Java 21, Spring Boot 3 et Angular 17. Toute contribution pull-request est la bienvenue !

markdown
Copier
Modifier

### Comment l’utiliser
1. **Copie/colle** le bloc dans `kflow/README.md`.  
2. Adapte le lien GitHub et l’identifiant admin si tu changes ces valeurs.  
3. Supprime les sections “Road-map” ou “Licence” si tu n’en veux pas.

Cela donne au lecteur (et au jury) toutes les informations objectives — installation, lancement, arborescence
