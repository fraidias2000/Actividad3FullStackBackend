@echo off

echo ========================================
echo Arrancando discovery-service
echo ========================================
start "discovery-service" cmd /k "cd /d %cd%\discovery-service && mvn spring-boot:run"

timeout /t 10 /nobreak

echo ========================================
echo Arrancando catalogue-service
echo ========================================
start "catalogue-service" cmd /k "cd /d %cd%\catalogue-service && mvn spring-boot:run"

timeout /t 5 /nobreak

echo ========================================
echo Arrancando orders-service
echo ========================================
start "orders-service" cmd /k "cd /d %cd%\orders-service && mvn spring-boot:run"

timeout /t 5 /nobreak

echo ========================================
echo Arrancando gateway-service
echo ========================================
start "gateway-service" cmd /k "cd /d %cd%\gateway-service && mvn spring-boot:run"

timeout /t 5 /nobreak

echo ========================================
echo Arrancando users-service
echo ========================================
start "users-service" cmd /k "cd /d %cd%\users-service && mvn spring-boot:run"

timeout /t 5 /nobreak

echo ========================================
echo Arrancando comms-service
echo ========================================
start "comms-service" cmd /k "cd /d "%cd%\comms-service" && set "MAIL_USERNAME=afraidias27@gmail.com" && set "MAIL_PASSWORD=ehmbbbgdrqxlsvba" && set "MAIL_FROM=afraidias27@gmail.com" && mvn spring-boot:run"

echo ========================================
echo Todos los microservicios se estan arrancando
echo ========================================
pause