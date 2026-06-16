@echo off

echo ========================================
echo Compilando catalogue-service
echo ========================================
cd catalogue-service
call mvn clean package -DskipTests
if errorlevel 1 goto error
cd ..

echo ========================================
echo Compilando discovery-service
echo ========================================
cd discovery-service
call mvn clean package -DskipTests
if errorlevel 1 goto error
cd ..

echo ========================================
echo Compilando gateway-service
echo ========================================
cd gateway-service
call mvn clean package -DskipTests
if errorlevel 1 goto error
cd ..

echo ========================================
echo Compilando orders-service
echo ========================================
cd orders-service
call mvn clean package -DskipTests
if errorlevel 1 goto error
cd ..

echo ========================================
echo Compilando users-service
echo ========================================
cd users-service
call mvn clean package -DskipTests
if errorlevel 1 goto error
cd ..

echo ========================================
echo Compilando comms-service
echo ========================================
cd comms-service
call mvn clean package -DskipTests
if errorlevel 1 goto error
cd ..

echo ========================================
echo Todos los microservicios compilados correctamente
echo ========================================
pause
exit /b 0

:error
echo.
echo ERROR: Ha fallado la compilacion.
echo Revisa el error anterior en la consola.
pause
exit /b 1