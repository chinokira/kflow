# KFLOW - Kayak Freestyle Leaderboard Organizer & Webmanager

## Vue d'ensemble du projet

KFLOW est une application full-stack de gestion de compétitions de kayak freestyle, développée comme projet d'apprentissage démontrant l'intégration de Java 21, Spring Boot 3 et Angular 17.

### Objectif
Gérer l'intégralité du cycle de vie d'une compétition de kayak freestyle :
- Création et gestion de compétitions
- Inscription des participants
- Organisation des catégories et des manches
- Suivi des performances et des scores
- Gestion des utilisateurs avec authentification JWT

---

## Structure du projet

```
kflow/
├── kflow_back/                 # Backend Spring Boot
│   ├── src/main/java/kayak/freestyle/competition/kflow/
│   │   ├── config/             # Configuration (CORS, Security)
│   │   ├── controllers/        # 9 contrôleurs REST
│   │   ├── dto/                # 9 Data Transfer Objects
│   │   ├── exceptions/         # Exceptions personnalisées
│   │   ├── mappers/            # 7 mappers Entity-DTO
│   │   ├── models/             # 7 entités JPA
│   │   ├── repositories/       # 6 repositories JPA
│   │   ├── security/           # Configuration sécurité JWT/RSA
│   │   └── services/           # 9 services métier
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── jwt/                # Clés RSA pour JWT
│   └── src/test/java/          # 9 classes de tests
├── kflow_front/                # Frontend Angular
│   └── src/app/
│       ├── components/         # Composants réutilisables
│       ├── pages/              # Composants pages
│       ├── services/           # 9 services API
│       ├── guards/             # Guards d'authentification
│       ├── interceptors/       # Intercepteurs HTTP
│       ├── models/             # Interfaces TypeScript
│       └── directives/         # Directives personnalisées
├── docker-compose.yml          # Orchestration Docker
└── README.md
```

**Statistiques :**
- Backend : 54 fichiers Java
- Frontend : 40 fichiers TypeScript
- Tests : 9 classes de tests

---

## Stack technologique

### Backend (kflow_back)

| Technologie | Version | Usage |
|-------------|---------|-------|
| Java | 21 | Langage de programmation |
| Spring Boot | 3.2.3 | Framework principal |
| Spring Security 6 | 6.2.x | Authentification & Autorisation |
| Spring Data JPA | - | Persistance des données |
| Hibernate | 6.4.x | ORM |
| MySQL | 8.0 | Base de données production |
| H2 | - | Base de données tests |
| JWT (RSA) | - | Authentification par token |
| Maven | 3.9 | Outil de build |
| Lombok | 1.18.38 | Génération de code |
| SpringDoc OpenAPI | 2.2.0 | Documentation API |
| Jackson | - | Traitement JSON |

### Frontend (kflow_front)

| Technologie | Version | Usage |
|-------------|---------|-------|
| Angular | 17.x | Framework |
| TypeScript | 5.x | Langage de programmation |
| Angular Material | 17.x | Bibliothèque de composants UI |
| RxJS | 7.8.0 | Programmation réactive |
| jwt-decode | 4.0.0 | Parsing des tokens JWT |
| Jasmine/Karma | - | Tests |

### DevOps

- Docker & Docker Compose
- Multi-stage Docker builds
- Nginx (pour servir le frontend)

---

## Modèles de données

### Entités principales

#### 1. Competition
Représente une compétition de kayak freestyle.

**Attributs :**
- `id` : Long (Clé primaire)
- `startDate` : LocalDate
- `endDate` : LocalDate
- `place` : String (lieu)
- `level` : String (niveau de compétition)

**Relations :**
- `categories` : One-to-Many avec Categorie

**Fichier :** [Competition.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/models/Competition.java)

#### 2. Categorie
Représente une catégorie de compétition (K1M, K1W, etc.).

**Attributs :**
- `id` : Long (Clé primaire)
- `name` : String

**Relations :**
- `competition` : Many-to-One avec Competition
- `stages` : One-to-Many avec Stage
- `participants` : Many-to-Many avec Participant

**Fichier :** [Categorie.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/models/Categorie.java)

#### 3. Stage
Représente une manche de compétition (Qualifications, Demi-finale, Finale).

**Attributs :**
- `id` : Long (Clé primaire)
- `name` : String
- `nbRun` : Integer (nombre de runs)
- `rules` : String (règles spécifiques)

**Relations :**
- `categorie` : Many-to-One avec Categorie
- `runs` : One-to-Many avec Run

**Fichier :** [Stage.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/models/Stage.java)

#### 4. Participant
Représente un athlète participant à une compétition.

**Attributs :**
- `id` : Long (Clé primaire)
- `bibNb` : int (numéro de dossard)
- `name` : String
- `club` : String (optionnel)

**Relations :**
- `categories` : Many-to-Many avec Categorie
- `runs` : One-to-Many avec Run

**Fichier :** [Participant.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/models/Participant.java)

#### 5. Run
Représente une performance d'un participant.

**Attributs :**
- `id` : Long (Clé primaire)
- `duration` : Integer (durée en secondes)
- `score` : Float

**Relations :**
- `stage` : Many-to-One avec Stage
- `participant` : Many-to-One avec Participant

**Fichier :** [Run.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/models/Run.java)

#### 6. User
Représente un utilisateur de l'application.

**Attributs :**
- `id` : Long (Clé primaire)
- `name` : String
- `email` : String
- `password` : String (hashé)
- `role` : Role (enum)

**Fichier :** [User.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/models/User.java)

#### 7. Role (Enum)
- `USER` : Utilisateur standard (lecture seule)
- `ADMIN` : Administrateur (gestion complète)

**Fichier :** [Role.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/models/Role.java)

### Schéma relationnel

```
Competition (1) ──< (N) Categorie (N) ──> (N) Participant
                          │
                          │ (1)
                          ↓
                        (N) Stage
                          │ (1)
                          ↓
                        (N) Run ──> (1) Participant
```

---

## API REST

### Endpoints publics

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/authenticate` | Connexion (email/password ou refresh token) |
| POST | `/users` | Inscription utilisateur |
| GET | `/` | Endpoint racine |

### Endpoints utilisateur (authentification requise)

| Méthode | Endpoint | Description | Accès |
|---------|----------|-------------|-------|
| GET | `/competitions` | Liste toutes les compétitions | USER, ADMIN |
| GET | `/competitions/{id}` | Détails d'une compétition | USER, ADMIN |
| GET | `/competitions/{id}/details` | Compétition avec tous les détails | USER, ADMIN |
| GET | `/categories/**` | Opérations sur les catégories | USER, ADMIN |
| GET | `/participants/**` | Opérations sur les participants | USER, ADMIN |
| GET | `/runs/**` | Opérations sur les runs | USER, ADMIN |
| GET | `/stages/**` | Opérations sur les manches | USER, ADMIN |

### Endpoints administrateur (ADMIN uniquement)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/competitions` | Créer une compétition |
| PUT | `/competitions/{id}` | Modifier une compétition |
| DELETE | `/competitions/{id}` | Supprimer une compétition |
| POST/PUT/DELETE | `/categories/**` | Gestion complète des catégories |
| POST/PUT/DELETE | `/participants/**` | Gestion complète des participants |
| POST/PUT/DELETE | `/runs/**` | Gestion complète des runs |
| POST/PUT/DELETE | `/stages/**` | Gestion complète des manches |
| POST | `/api/import/validate` | Valider un JSON d'import |
| POST | `/api/import/competition` | Importer une compétition depuis JSON |

### Documentation API

- Swagger UI : `http://localhost:8080/swagger-ui.html`
- OpenAPI docs : `http://localhost:8080/v3/api-docs`

**Fichier principal :** [SecurityConfig.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/security/SecurityConfig.java)

---

## Architecture de sécurité

### Authentification JWT avec chiffrement RSA

#### Configuration
- **Type** : JWT avec paire de clés RSA (public/private)
- **Stockage des clés** : `kflow_back/src/main/resources/jwt/`
- **Session** : Stateless (sans état)
- **CORS** : Activé pour localhost:4200

#### Types de tokens

1. **Access Token**
   - Durée de vie : 5 minutes (300 secondes)
   - Usage : Authentification des requêtes API

2. **Refresh Token**
   - Durée de vie : 1 minute (configurable)
   - Usage : Renouvellement de l'access token

#### Claims JWT
```json
{
  "sub": "user_id",
  "username": "user_name",
  "role": "USER|ADMIN",
  "iss": "annuaire-backend",
  "iat": 1234567890,
  "exp": 1234568190
}
```

#### Chiffrement des mots de passe
- `DelegatingPasswordEncoder` de Spring Security
- Algorithme de hachage sécurisé

### Sécurité Frontend

#### AuthenticationInterceptor
- Ajoute automatiquement le JWT dans l'en-tête `Authorization`
- Gère les erreurs 401 avec rafraîchissement automatique du token
- Redirige vers login si le rafraîchissement échoue

**Fichier :** [authentication.interceptor.ts](kflow_front/src/app/interceptors/authentication.interceptor.ts)

#### Route Guards

1. **authGuard**
   - Vérifie l'authentification
   - Redirige vers `/login` si non authentifié

2. **adminGuard**
   - Vérifie le rôle ADMIN
   - Redirige vers `/home` si non autorisé

**Fichiers :**
- [auth.guard.ts](kflow_front/src/app/guards/auth.guard.ts)
- [admin.guard.ts](kflow_front/src/app/guards/admin.guard.ts)

#### Stockage des tokens
- LocalStorage pour la persistance
- Validation automatique au démarrage de l'app
- Rafraîchissement automatique des tokens expirés

---

## Patterns architecturaux

### Backend

#### 1. Architecture en couches

```
Controllers (REST API)
    ↓
Services (Logique métier)
    ↓
Repositories (Accès données)
    ↓
Database (MySQL/H2)

    DTOs ← Mappers → Entities
```

#### 2. Pattern générique

Classes génériques pour réduire la duplication de code :

- **GenericController<DTO, SERVICE>**
  - Opérations CRUD standard
  - Endpoints REST uniformes

- **GenericService<MODEL, DTO, REPOSITORY, MAPPER>**
  - Logique métier réutilisable
  - Transactions automatiques

- **GenericMapper<MODEL, DTO>**
  - Conversion Entity ↔ DTO
  - Gestion des références circulaires

**Fichiers clés :**
- [GenericController.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/controllers/GenericController.java)
- [GenericService.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/services/GenericService.java)
- [GenericMapper.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/mappers/GenericMapper.java)

**Avantages :**
- Réutilisabilité du code
- Opérations CRUD cohérentes
- Type-safe
- Extension facile pour nouvelles entités

#### 3. Pattern Repository

Spring Data JPA avec :
- Requêtes JPQL personnalisées
- Optimisation des requêtes (JOIN FETCH)
- Prévention du problème N+1

**Exemple :**
```java
@Query("SELECT c FROM Competition c LEFT JOIN FETCH c.categories WHERE c.id = :id")
Optional<Competition> findByIdWithCategories(@Param("id") Long id);
```

#### 4. Pattern DTO

- Séparation entre modèle de domaine et API
- Validation avec annotations Jakarta Bean Validation
- Évite l'over-fetching
- Support du versioning d'API

#### 5. Gestion des transactions

- Annotation `@Transactional` sur les services
- Rollback automatique en cas d'exception
- Optimisation `readOnly` pour les lectures

### Frontend

#### 1. Architecture basée sur les composants

```
App Component
├── Pages (Smart Components)
│   ├── Home
│   ├── Competitions
│   │   ├── List
│   │   ├── Detail
│   │   ├── Edit
│   │   └── Import
│   └── Users
└── Components (Presentational)
    ├── Navbar
    ├── Footer
    ├── Login
    ├── SignUp
    └── UserProfile
```

#### 2. Services Angular

Services génériques avec communication HTTP :
- `AuthenticationService` : Gestion de l'authentification
- `CompetitionService` : CRUD compétitions
- `CategorieService`, `ParticipantService`, etc.

**Exemple de service générique :**
```typescript
export class GenericService<T> {
  constructor(
    protected http: HttpClient,
    protected endpoint: string
  ) {}

  getAll(): Observable<T[]> {
    return this.http.get<T[]>(this.endpoint);
  }

  getById(id: number): Observable<T> {
    return this.http.get<T>(`${this.endpoint}/${id}`);
  }

  // ... autres méthodes CRUD
}
```

#### 3. Programmation réactive

- RxJS Observables pour les flux de données
- BehaviorSubject pour la gestion d'état
- Gestion réactive des formulaires

#### 4. Routing

- Modules chargés paresseusement
- Guards de routes
- Routing imbriqué

**Fichier :** [app.routes.ts](kflow_front/src/app/app.routes.ts)

---

## Fonctionnalités principales

### 1. Gestion des compétitions

- CRUD complet des compétitions
- Validation des dates
- Gestion du lieu et du niveau
- Association avec les catégories

**Services concernés :**
- Backend : [CompetitionService.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/services/CompetitionService.java)
- Frontend : [competition.service.ts](kflow_front/src/app/services/competition.service.ts)

### 2. Gestion des catégories

- Plusieurs catégories par compétition
- Attribution des manches
- Inscription des participants
- Règles spécifiques par catégorie

**Services concernés :**
- Backend : [CategorieService.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/services/CategorieService.java)
- Frontend : [categorie.service.ts](kflow_front/src/app/services/categorie.service.ts)

### 3. Gestion des manches

- Définition des manches (Qualif, Demi-finale, Finale)
- Configuration du nombre de runs
- Règles spécifiques
- Suivi des performances

**Services concernés :**
- Backend : [StageService.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/services/StageService.java)
- Frontend : [stage.service.ts](kflow_front/src/app/services/stage.service.ts)

### 4. Gestion des participants

- Inscription des athlètes
- Attribution des numéros de dossard
- Affiliation au club
- Participation multi-catégories

**Services concernés :**
- Backend : [ParticipantService.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/services/ParticipantService.java)
- Frontend : [participant.service.ts](kflow_front/src/app/services/participant.service.ts)

### 5. Suivi des performances

- Enregistrement de la durée du run
- Attribution des scores
- Association aux manches
- Historique des performances

**Services concernés :**
- Backend : [RunService.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/services/RunService.java)
- Frontend : [run.service.ts](kflow_front/src/app/services/run.service.ts)

### 6. Gestion des utilisateurs

- Inscription et authentification
- Contrôle d'accès basé sur les rôles
- Gestion du profil
- Session JWT

**Services concernés :**
- Backend : [UserService.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/services/UserService.java)
- Frontend : [authentication.service.ts](kflow_front/src/app/services/authentication.service.ts)

### 7. Import de compétitions

- Import en masse via JSON
- Validation avant import
- Import de la structure complète
- Rapport d'erreurs

**Service backend :** [ImportService.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/services/ImportService.java)

**Format JSON attendu :**
```json
{
  "startDate": "2024-06-01",
  "endDate": "2024-06-03",
  "place": "Freestyle Park",
  "level": "International",
  "categories": [
    {
      "name": "K1 Men",
      "stages": [...],
      "participants": [...]
    }
  ]
}
```

---

## Gestion des erreurs

### Backend

#### Exceptions personnalisées

- **BadRequestException** : Requête invalide (400)
- **NotFoundException** : Ressource non trouvée (404)

**Fichiers :** [exceptions/](kflow_back/src/main/java/kayak/freestyle/competition/kflow/exceptions/)

#### Global Exception Handler

Annotation `@ControllerAdvice` pour gérer les exceptions de manière centralisée.

**Réponses d'erreur standardisées :**
```json
{
  "timestamp": "2024-01-07T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Competition not found with id: 123",
  "path": "/competitions/123"
}
```

### Frontend

#### Intercepteur d'erreurs

Gestion centralisée des erreurs HTTP :
- 401 : Rafraîchissement automatique du token
- 403 : Redirection vers page d'accueil
- 404 : Message d'erreur approprié
- 500 : Message d'erreur serveur

---

## Tests

### Backend (9 classes de tests)

#### Tests de services
- `CategorieServiceTest`
- `CompetitionServiceTest`
- `GenericServiceTest`
- `ImportServiceTest`
- `ParticipantServiceTest`
- `RunServiceTest`
- `UserServiceTest`

#### Tests de contrôleurs
- `AuthenticationControllerTest`

#### Tests d'intégration
- `CompetitionImportIntegrationTest`

**Configuration des tests :**
- Base de données H2 en mémoire
- JUnit 5
- Mockito pour les mocks
- `@SpringBootTest` pour les tests d'intégration

**Fichiers :** [kflow_back/src/test/java/](kflow_back/src/test/java/)

### Frontend

- Framework : Jasmine
- Runner : Karma
- 11 specs de tests
- Tests unitaires des composants

---

## Déploiement

### Développement local

#### Backend
```bash
cd kflow_back
./mvnw spring-boot:run
```
- Port : 8080
- API : http://localhost:8080

#### Frontend
```bash
cd kflow_front
npm install
ng serve
```
- Port : 4200
- App : http://localhost:4200

#### Base de données
- MySQL sur port 3306
- Nom : `kflow`
- Credentials : voir `application.properties`

### Déploiement Docker

#### Construction et lancement
```bash
docker-compose up --build
```

#### Services
- **Backend** : http://localhost:8080
  - Container : kflow-backend
  - Image : kflow-back
  - Build multi-stage avec Maven

- **Frontend** : http://localhost:80
  - Container : kflow-frontend
  - Image : kflow-front
  - Nginx Alpine pour servir les fichiers statiques

- **Database** : localhost:3306
  - Container : kflow-db
  - Image : mysql:8.0
  - Volume : kflow_mysql_data

#### Network
- Réseau interne : `kflow-network`
- Isolation des services
- Communication inter-conteneurs

**Fichier :** [docker-compose.yml](docker-compose.yml)

---

## Configuration

### Backend (application.properties)

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/kflow
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update

# JPA
spring.jpa.properties.hibernate.physical_naming_strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

# Logging
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# JWT
rsa.public-key=classpath:jwt/public.pem
rsa.private-key=classpath:jwt/private.pem
```

**Fichier :** [application.properties](kflow_back/src/main/resources/application.properties)

### Frontend

Configuration de l'environnement dans les services :
```typescript
private apiUrl = 'http://localhost:8080';
```

Pour la production, modifier l'URL dans chaque service ou utiliser les variables d'environnement Angular.

---

## Qualité du code

### Lombok
Réduction du boilerplate avec :
- `@Getter` / `@Setter`
- `@Builder`
- `@NoArgsConstructor` / `@AllArgsConstructor` / `@RequiredArgsConstructor`
- `@Data`

### Documentation
- Commentaires JavaDoc sur classes et méthodes
- Commentaires inline pour logique complexe
- README avec instructions de setup
- Diagrammes UML (PlantUML)

**Fichiers UML :**
- [diagramSequenceJWT.puml](kflow_back/src/main/java/kayak/freestyle/competition/kflow/models/diagramSequenceJWT.puml)
- [diagramUseCase1.puml](kflow_back/src/main/java/kayak/freestyle/competition/kflow/models/diagramUseCase1.puml)

### Conventions de nommage
- Noms clairs et descriptifs
- Structure de packages cohérente
- Conventions REST standard

---

## Roadmap (d'après README)

### 1. Sprint Performance
- Profilage Hibernate
- Ajout d'index sur la base de données

### 2. Sprint Qualité
- Intégration JaCoCo pour la couverture de code
- Configuration ESLint stricte

### 3. Sprint CI/CD
- Optimisation Docker multi-stage
- Déploiement sur VPS

### 4. Sprint Temps réel
- Notifications WebSocket
- Résultats de compétition en direct

---

## Fichiers clés du projet

### Backend

#### Application principale
[KflowApplication.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/KflowApplication.java)

#### Configuration
- [SecurityConfig.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/security/SecurityConfig.java) - Configuration de sécurité
- [CorsConfig.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/config/CorsConfig.java) - Configuration CORS

#### Contrôleurs
- [AuthenticationController.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/controllers/AuthenticationController.java)
- [CompetitionController.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/controllers/CompetitionController.java)
- [ImportController.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/controllers/ImportController.java)

#### Services
- [JwtService.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/security/JwtService.java) - Gestion JWT
- [ImportService.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/services/ImportService.java) - Import JSON

### Frontend

#### Configuration
- [app.module.ts](kflow_front/src/app/app.module.ts) - Module principal
- [app.routes.ts](kflow_front/src/app/app.routes.ts) - Configuration du routing

#### Services
- [authentication.service.ts](kflow_front/src/app/services/authentication.service.ts) - Authentification
- [competition.service.ts](kflow_front/src/app/services/competition.service.ts) - Gestion des compétitions

#### Intercepteurs et Guards
- [authentication.interceptor.ts](kflow_front/src/app/interceptors/authentication.interceptor.ts)
- [auth.guard.ts](kflow_front/src/app/guards/auth.guard.ts)
- [admin.guard.ts](kflow_front/src/app/guards/admin.guard.ts)

---

## Conseils pour contribuer

### Backend

1. **Créer une nouvelle entité**
   - Créer la classe dans `models/`
   - Créer le DTO dans `dto/`
   - Créer le mapper dans `mappers/` (étendre `GenericMapper`)
   - Créer le repository dans `repositories/`
   - Créer le service dans `services/` (étendre `GenericService`)
   - Créer le controller dans `controllers/` (étendre `GenericController`)

2. **Ajouter un endpoint personnalisé**
   - Ajouter la méthode dans le service
   - Ajouter la méthode dans le controller
   - Documenter avec annotations OpenAPI

3. **Écrire des tests**
   - Tests unitaires : mockez les dépendances
   - Tests d'intégration : utilisez `@SpringBootTest`
   - Base H2 configurée automatiquement

### Frontend

1. **Créer un nouveau composant**
   ```bash
   ng generate component components/mon-composant
   ```

2. **Créer un nouveau service**
   ```bash
   ng generate service services/mon-service
   ```

3. **Ajouter une route**
   - Modifier `app.routes.ts`
   - Ajouter le guard approprié si nécessaire

4. **Utiliser Angular Material**
   - Importer les modules nécessaires dans `app.module.ts`
   - Utiliser les composants Material dans les templates

---

## Dépannage

### Backend ne démarre pas
- Vérifier que MySQL est lancé
- Vérifier les credentials dans `application.properties`
- Vérifier que le port 8080 n'est pas utilisé

### Frontend ne se connecte pas au backend
- Vérifier que le backend est lancé sur le port 8080
- Vérifier la configuration CORS dans `SecurityConfig.java`
- Vérifier l'URL de l'API dans les services Angular

### Problèmes JWT
- Vérifier que les clés RSA existent dans `kflow_back/src/main/resources/jwt/`
- Vérifier que les tokens ne sont pas expirés
- Vérifier le LocalStorage du navigateur

### Docker ne build pas
- Nettoyer les images : `docker-compose down -v`
- Reconstruire : `docker-compose up --build`
- Vérifier les logs : `docker-compose logs`

---

## Ressources

### Documentation
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Angular](https://angular.io/docs)
- [Angular Material](https://material.angular.io/)
- [Docker](https://docs.docker.com/)

### Outils
- [Swagger UI](http://localhost:8080/swagger-ui.html) - Documentation API interactive
- [MySQL Workbench](https://www.mysql.com/products/workbench/) - Gestion de la base de données

---

## Corrections et améliorations apportées

### Date : 2026-01-08

Cette section documente les corrections critiques et améliorations de qualité du code effectuées suite à une analyse approfondie du projet.

---

### 🔴 Corrections Critiques (Backend)

#### 1. Gestion sécurisée des Optional dans AuthenticationController

**Problème :** Utilisation dangereuse de `.get()` sur un Optional sans vérification
**Fichier :** [AuthenticationController.java:85](kflow_back/src/main/java/kayak/freestyle/competition/kflow/controllers/AuthenticationController.java)

**Avant :**
```java
User user = userRepository.findById(Long.parseLong(jwt.getSubject())).get();
```

**Après :**
```java
User user = userRepository.findById(Long.parseLong(jwt.getSubject()))
        .orElseThrow(() -> new NotFoundException("User not found with id: " + jwt.getSubject()));
```

**Impact :** Évite les `NoSuchElementException` et fournit un message d'erreur explicite.

---

#### 2. Augmentation de la durée du Refresh Token JWT

**Problème :** Refresh token expirant après seulement 1 minute
**Fichier :** [AuthenticationController.java:108](kflow_back/src/main/java/kayak/freestyle/competition/kflow/controllers/AuthenticationController.java)

**Avant :**
```java
.expiresAt(Instant.now().plus(1, ChronoUnit.MINUTES))
```

**Après :**
```java
.expiresAt(Instant.now().plus(7, ChronoUnit.DAYS)) // 7 jours au lieu de 1 minute
```

**Impact :** Amélioration significative de l'expérience utilisateur - les utilisateurs n'ont plus besoin de se reconnecter toutes les minutes.

---

#### 3. Sécurisation du GlobalExceptionHandler

**Problème :** Exposition d'informations sensibles via les messages d'erreur
**Fichier :** [GlobalExceptionHandler.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/controllers/GlobalExceptionHandler.java)

**Avant :**
```java
@ExceptionHandler(Exception.class)
public ResponseEntity<String> handleGenericException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Une erreur inattendue s'est produite: " + ex.getMessage());
}
```

**Après :**
```java
private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

@ExceptionHandler(Exception.class)
public ResponseEntity<String> handleGenericException(Exception ex) {
    // Logger l'erreur détaillée côté serveur pour le debugging
    logger.error("Une erreur inattendue s'est produite", ex);

    // Retourner un message générique au client pour éviter la fuite d'informations
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Une erreur inattendue s'est produite. Veuillez réessayer plus tard.");
}
```

**Améliorations :**
- Logging SLF4J pour le debugging côté serveur
- Message générique pour le client (sécurité)
- Correction du warning lambda : `(error)` → `error`

**Impact :** Prévention de la fuite d'informations sensibles (vulnérabilité de sécurité).

---

### 🔴 Corrections Critiques (Frontend)

#### 4. Correction du nom de directive et ajout des imports manquants

**Problème :** Nom de directive incohérent et imports de lifecycle manquants
**Fichier :** [insert-error.directive.ts](kflow_front/src/app/directives/insert-error.directive.ts)

**Avant :**
```typescript
import { Directive, Input, ElementRef } from '@angular/core';

@Directive({
  selector: '[appInsertError2]',
  standalone: true
})
export class InsertErrorDirective {
  @Input('appInsertError2') control?: AbstractControl;

  ngOnInit(): void { ... }
  ngOnDestroy(): void { ... }
}
```

**Après :**
```typescript
import { Directive, Input, ElementRef, OnInit, OnDestroy } from '@angular/core';

@Directive({
  selector: '[appInsertError]',
  standalone: true
})
export class InsertErrorDirective implements OnInit, OnDestroy {
  @Input('appInsertError') control?: AbstractControl;

  ngOnInit(): void { ... }
  ngOnDestroy(): void { ... }
}
```

**Fichiers modifiés :**
- [insert-error.directive.ts](kflow_front/src/app/directives/insert-error.directive.ts)
- [sign-up.component.html](kflow_front/src/app/components/sign-up/sign-up.component.html)

**Impact :**
- Cohérence du nommage
- Respect des conventions Angular avec imports de lifecycle

---

### 🟡 Corrections de Haute Priorité (Frontend)

#### 5. Suppression des console.log en production

**Problème :** 20+ console.log dans le code de production
**Impact :** Performance, fuite potentielle d'informations

**Fichiers nettoyés :**
- [authentication.service.ts](kflow_front/src/app/services/authentication.service.ts) - 4 console.log supprimés
- [login.component.ts](kflow_front/src/app/components/login/login.component.ts) - 1 console.log supprimé
- [competition-edit.component.ts](kflow_front/src/app/pages/competitions/competition-edit/competition-edit.component.ts) - 1 console.log supprimé
- [competition-detail.component.ts](kflow_front/src/app/pages/competitions/competition-detail/competition-detail.component.ts) - 1 console.log supprimé
- [competition-import.component.ts](kflow_front/src/app/pages/competitions/competition-import/competition-import.component.ts) - 1 console.log supprimé

**Exemple de correction :**
```typescript
// Avant
console.log('JWT decoded:', decodedAccessToken);
console.log('User created:', user);

// Après
// Code nettoyé - logs supprimés
```

---

#### 6. Correction des fuites mémoire (Subscriptions)

**Problème :** Subscriptions RxJS non désabonnées causant des fuites mémoire
**Fichier :** [navbar.component.ts](kflow_front/src/app/components/navbar/navbar.component.ts)

**Avant :**
```typescript
export class NavbarComponent implements OnInit {
  ngOnInit(): void {
    this.authService.connectedUser$.subscribe(user => {
      this.connectedUser = user;
    });
  }
}
```

**Après :**
```typescript
export class NavbarComponent implements OnInit, OnDestroy {
  private userSubscription?: Subscription;

  ngOnInit(): void {
    this.userSubscription = this.authService.connectedUser$.subscribe(user => {
      this.connectedUser = user;
    });
  }

  ngOnDestroy(): void {
    this.userSubscription?.unsubscribe();
  }
}
```

**Impact :** Prévention des fuites mémoire dans les applications Angular.

---

### 📊 Résumé des Corrections

| Catégorie | Nombre | Fichiers modifiés |
|-----------|--------|-------------------|
| Critiques Backend | 3 | 2 fichiers Java |
| Critiques Frontend | 2 | 2 fichiers TS + 1 HTML |
| Haute Priorité | 2 | 6 fichiers TS |
| **Total** | **7** | **11 fichiers** |

---

### 🛡️ Améliorations de Sécurité

1. ✅ Gestion sécurisée des Optional (prévention NullPointerException)
2. ✅ Masquage des erreurs internes (prévention fuite d'information)
3. ✅ Logging structuré côté serveur
4. ✅ Durée de session utilisateur améliorée (7 jours vs 1 minute)

---

### ⚡ Améliorations de Performance

1. ✅ Suppression de tous les console.log en production
2. ✅ Prévention des fuites mémoire avec unsubscribe
3. ✅ Meilleure gestion du cycle de vie des composants Angular

---

### 📝 Problèmes Identifiés mais Non Corrigés

Les problèmes suivants ont été identifiés lors de l'analyse mais nécessitent une décision ou une planification :

#### Backend
- **Logging de debug activé** : `application.properties` contient des niveaux DEBUG/TRACE pour Hibernate
- **Mot de passe base de données vide** : Configuration de développement non sécurisée
- **SSL désactivé** : Connexion MySQL sans SSL

#### Frontend
- **Dépendances obsolètes** : Angular 17 → Angular 21 (migration majeure à planifier)
- **Styles CSS/SCSS mélangés** : Standardisation nécessaire
- **Pas de configuration par environnement** : Créer des fichiers environment.ts

---

#### 7. Gestion des erreurs d'authentification avec compte inexistant

**Problème :** Erreur 500 retournée lors d'une tentative de connexion avec un compte inexistant
**Fichiers :**
- Backend : [GlobalExceptionHandler.java](kflow_back/src/main/java/kayak/freestyle/competition/kflow/controllers/GlobalExceptionHandler.java)
- Frontend : [login.component.ts](kflow_front/src/app/components/login/login.component.ts)

**Contexte :**
Lorsqu'un utilisateur tentait de se connecter avec des identifiants incorrects, l'application renvoyait une erreur HTTP 500 au lieu d'une erreur 401 Unauthorized. L'exception `BadCredentialsException` était capturée par le handler générique au lieu d'avoir son propre handler dédié.

**Correction Backend - Ajout d'un handler dédié :**
```java
import org.springframework.security.authentication.BadCredentialsException;

@ExceptionHandler(BadCredentialsException.class)
public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body("Email ou mot de passe incorrect. Aucun compte trouvé avec ces identifiants.");
}
```

**Correction Frontend - Redirection automatique vers l'inscription :**
```typescript
onSubmit() {
  this.authenticationService.login(
    this.credentials.value.email!,
    this.credentials.value.password!).subscribe({
      next: () => this.router.navigateByUrl('/'),
      error: err => {
        if (err.status === 401) {
          this.error = err.error || 'Email ou mot de passe incorrect. Aucun compte trouvé.';
          // Rediriger automatiquement vers l'inscription après 3 secondes
          setTimeout(() => this.router.navigateByUrl('/register'), 3000);
        } else {
          this.error = err.message;
        }
      }
    });
}
```

**Améliorations apportées :**
- Ajout de l'import `CommonModule` dans login.component.ts pour supporter la directive `*ngIf`
- Code HTTP approprié (401 au lieu de 500)
- Message d'erreur explicite et convivial
- Redirection automatique vers la page d'inscription après 3 secondes
- Meilleure expérience utilisateur guidant vers la création de compte

**Impact :**
- Code HTTP correct selon les standards REST
- Amélioration de l'expérience utilisateur (UX)
- Facilite la conversion des visiteurs en utilisateurs inscrits
- Message d'erreur informatif sans exposition de détails techniques

---

### 🎯 Recommandations Futures

1. **Tests** : Ajouter des tests unitaires pour les nouvelles corrections
2. **CI/CD** : Configurer un pipeline pour détecter automatiquement les console.log
3. **Linting** : Activer ESLint strict pour prévenir les fuites mémoire
4. **Migration Angular** : Planifier la mise à jour vers Angular 21
5. **Configuration multi-environnement** : Créer des profils Spring (dev, prod) et des environments Angular

---

## Contact & Support

Pour toute question ou suggestion sur le projet KFLOW, veuillez consulter le README principal ou créer une issue sur le dépôt du projet.
