#!/bin/bash
# Script d'analyse SonarQube pour KFLOW
# Ce script compile le projet, exécute les tests avec couverture de code et lance l'analyse SonarQube

set -e  # Arrêter en cas d'erreur

echo "==========================================="
echo "  KFLOW - Analyse SonarQube"
echo "==========================================="
echo ""

# Couleurs pour les messages
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Vérifier que SonarQube est accessible
echo -e "${YELLOW}Vérification de la disponibilité de SonarQube...${NC}"
SONAR_URL="${SONAR_HOST_URL:-http://localhost:9000}"
if curl -s --head "$SONAR_URL/api/system/status" | head -n 1 | grep "200\|301" > /dev/null; then
    echo -e "${GREEN}✓ SonarQube est accessible${NC}"
else
    echo -e "${RED}✗ SonarQube n'est pas accessible à $SONAR_URL${NC}"
    echo "  Assurez-vous que SonarQube est démarré."
    exit 1
fi

echo ""
echo "==========================================="
echo "  1. BACKEND - Tests et Couverture"
echo "==========================================="
cd kflow_back

echo -e "${YELLOW}Nettoyage des anciens rapports...${NC}"
./mvnw clean -q

echo -e "${YELLOW}Compilation et exécution des tests avec JaCoCo...${NC}"
./mvnw test jacoco:report -q

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Tests backend exécutés avec succès${NC}"

    # Afficher le résumé de la couverture
    if [ -f "target/site/jacoco/index.html" ]; then
        echo -e "${GREEN}  Rapport JaCoCo disponible: kflow_back/target/site/jacoco/index.html${NC}"
    fi
else
    echo -e "${RED}✗ Échec des tests backend${NC}"
    exit 1
fi

cd ..

echo ""
echo "==========================================="
echo "  2. FRONTEND - Tests et Couverture"
echo "==========================================="
cd kflow_front

echo -e "${YELLOW}Installation des dépendances si nécessaire...${NC}"
if [ ! -d "node_modules" ]; then
    npm install --silent
fi

echo -e "${YELLOW}Exécution des tests avec couverture...${NC}"
npm run test -- --watch=false --code-coverage --browsers=ChromeHeadless 2>&1 | grep -E "(TOTAL|✔|✗)" || true

if [ -d "coverage" ]; then
    echo -e "${GREEN}✓ Tests frontend exécutés${NC}"
    echo -e "${GREEN}  Rapport de couverture disponible: kflow_front/coverage/index.html${NC}"
else
    echo -e "${YELLOW}⚠ Couverture frontend non générée (Chrome peut ne pas être disponible)${NC}"
fi

cd ..

echo ""
echo "==========================================="
echo "  3. ANALYSE SONARQUBE"
echo "==========================================="

# Vérifier la présence du token
if [ -z "$SONAR_TOKEN" ]; then
    echo -e "${YELLOW}⚠ Variable SONAR_TOKEN non définie${NC}"
    echo "  Pour générer un token: SonarQube > My Account > Security > Generate Tokens"
    echo "  Puis: export SONAR_TOKEN=your_token_here"
    echo ""
    echo -e "${YELLOW}Analyse sans authentification (peut échouer)...${NC}"
    SONAR_ARGS=""
else
    echo -e "${GREEN}✓ Token SonarQube détecté${NC}"
    SONAR_ARGS="-Dsonar.token=$SONAR_TOKEN"
fi

echo -e "${YELLOW}Lancement de l'analyse SonarQube...${NC}"

# Utiliser le scanner Maven pour analyser tout le projet
cd kflow_back
./mvnw sonar:sonar \
    -Dsonar.host.url="$SONAR_URL" \
    -Dsonar.projectBaseDir=.. \
    $SONAR_ARGS

if [ $? -eq 0 ]; then
    echo ""
    echo -e "${GREEN}==========================================="
    echo -e "  ✓ Analyse SonarQube terminée"
    echo -e "==========================================${NC}"
    echo ""
    echo -e "Consultez les résultats sur: ${GREEN}$SONAR_URL/dashboard?id=kflow${NC}"
else
    echo -e "${RED}✗ Échec de l'analyse SonarQube${NC}"
    exit 1
fi
