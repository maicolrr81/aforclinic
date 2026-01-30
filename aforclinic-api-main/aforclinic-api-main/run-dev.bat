@echo off
chcp 65001 >nul

echo ========================================
echo API Server Development Mode
echo ========================================
echo.

cd /d "%~dp0"

echo Current directory: %CD%
echo.

REM Check Java
where java >nul 2>&1
set JAVA_CHECK=%ERRORLEVEL%
if %JAVA_CHECK% EQU 0 (
    goto :java_ok
)
echo ERROR: Java is not installed.
echo Please install Java 21 (Amazon Corretto).
pause
exit /b 1
:java_ok

echo Java version:
java -version
echo.

REM Build JAR file
echo Building JAR file...
call gradlew.bat clean build -x test
set BUILD_ERROR=%ERRORLEVEL%
if %BUILD_ERROR% EQU 0 (
    goto :build_ok
)
echo.
echo ERROR: Build failed
pause
exit /b %BUILD_ERROR%
:build_ok

echo.
echo Build completed successfully!
echo.

REM Check JAR file
set JAR_FILE=build\libs\aforclinic-api-0.0.1-SNAPSHOT.jar
if not exist "%JAR_FILE%" (
    echo ERROR: JAR file not found: %JAR_FILE%
    pause
    exit /b 1
)

echo ========================================
echo Starting Spring Boot Application
echo ========================================
echo Profile: local (development)
echo Port: 9090
echo API Base: http://localhost:9090
echo.
echo Press Ctrl+C to stop the server
echo.

REM Run JAR file
java -jar "%JAR_FILE%" --spring.profiles.active=local

if %ERRORLEVEL% neq 0 (
    echo.
    echo ERROR: Application failed to start
    pause
    exit /b %ERRORLEVEL%
)

pause

