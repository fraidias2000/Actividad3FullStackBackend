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
echo Arrancando comms-service
echo ========================================
start "comms-service" cmd /k "cd /d %cd%\comms-service && mvn spring-boot:run"

echo ========================================
echo Todos los microservicios se estan arrancando
echo ========================================
pause