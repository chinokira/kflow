@echo off
REM Script d'analyse SonarQube pour KFLOW (Windows)
REM Ce script compile le projet, exécute les tests avec couverture de code et lance l'analyse SonarQube

echo ===========================================
echo   KFLOW - Analyse SonarQube
echo ===========================================
echo.

REM Vérifier que SonarQube est accessible
echo Verification de la disponibilite de SonarQube...
if "%SONAR_HOST_URL%"=="" set SONAR_HOST_URL=http://localhost:9000

curl -s --head "%SONAR_HOST_URL%/api/system/status" | findstr "200 301" >nul
if %errorlevel% equ 0 (
    echo [OK] SonarQube est accessible
) else (
    echo [ERREUR] SonarQube n'est pas accessible a %SONAR_HOST_URL%
    echo Assurez-vous que SonarQube est demarre.
    exit /b 1
)

echo.
echo ===========================================
echo   1. BACKEND - Tests et Couverture
echo ===========================================
cd kflow_back

echo Nettoyage des anciens rapports...
call mvnw.cmd clean -q

echo Compilation et execution des tests avec JaCoCo...
call mvnw.cmd test jacoco:report -q

if %errorlevel% equ 0 (
    echo [OK] Tests backend executes avec succes

    if exist "target\site\jacoco\index.html" (
        echo   Rapport JaCoCo disponible: kflow_back\target\site\jacoco\index.html
    )
) else (
    echo [ERREUR] Echec des tests backend
    exit /b 1
)

cd ..

echo.
echo ===========================================
echo   2. FRONTEND - Tests et Couverture
echo ===========================================
cd kflow_front

echo Installation des dependances si necessaire...
if not exist "node_modules\" (
    call npm install --silent
)

echo Execution des tests avec couverture...
call npm run test -- --watch=false --code-coverage --browsers=ChromeHeadless 2>nul

if exist "coverage\" (
    echo [OK] Tests frontend executes
    echo   Rapport de couverture disponible: kflow_front\coverage\index.html
) else (
    echo [AVERTISSEMENT] Couverture frontend non generee (Chrome peut ne pas etre disponible)
)

cd ..

echo.
echo ===========================================
echo   3. ANALYSE SONARQUBE
echo ===========================================

REM Vérifier la présence du token
if "%SONAR_TOKEN%"=="" (
    echo [AVERTISSEMENT] Variable SONAR_TOKEN non definie
    echo   Pour generer un token: SonarQube ^> My Account ^> Security ^> Generate Tokens
    echo   Puis: set SONAR_TOKEN=your_token_here
    echo.
    echo Analyse sans authentification (peut echouer)...
    set SONAR_ARGS=
) else (
    echo [OK] Token SonarQube detecte
    set SONAR_ARGS=-Dsonar.token=%SONAR_TOKEN%
)

echo Lancement de l'analyse SonarQube...

cd kflow_back
call mvnw.cmd sonar:sonar ^
    -Dsonar.host.url="%SONAR_HOST_URL%" ^
    -Dsonar.projectBaseDir=.. ^
    %SONAR_ARGS%

if %errorlevel% equ 0 (
    echo.
    echo ===========================================
    echo   [OK] Analyse SonarQube terminee
    echo ===========================================
    echo.
    echo Consultez les resultats sur: %SONAR_HOST_URL%/dashboard?id=kflow
) else (
    echo [ERREUR] Echec de l'analyse SonarQube
    exit /b 1
)

cd ..
