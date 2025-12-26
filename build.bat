@echo off
chcp 65001 >nul

echo ========================================
echo AforClinic Build Script
echo ========================================
echo;

cd /d "%~dp0"

echo Current directory: %CD%
echo;

set OUTPUT_DIR=output
set API_DIR=aforclinic-api-main\aforclinic-api-main
set WEB_DIR=aforclinic-web-main\aforclinic-web-main
set ADMIN_DIR=aforclinic-admin-main\aforclinic-admin-main
set ROOT_DIR=%~dp0

echo Creating output directory...
if exist "%OUTPUT_DIR%" rmdir /S /Q "%OUTPUT_DIR%"
mkdir "%OUTPUT_DIR%"
mkdir "%OUTPUT_DIR%\api"
mkdir "%OUTPUT_DIR%\web"
mkdir "%OUTPUT_DIR%\admin"
echo;

REM ========================================
REM 1. API Build
REM ========================================
echo Building API - Step 1 of 3

if not exist "%API_DIR%" (
    echo ERROR: API directory not found
    pause
    exit /b 1
)

cd /d "%API_DIR%"

where java >nul 2>&1
set JAVA_CHECK_ERROR=%ERRORLEVEL%
if %JAVA_CHECK_ERROR% EQU 0 (
    goto :java_ok
)
echo ERROR: Java is not installed.
echo Please install Java 21 (Amazon Corretto).
cd /d "%ROOT_DIR%"
pause
exit /b 1
:java_ok

echo Building JAR file...
call gradlew.bat clean build -x test
if %ERRORLEVEL% neq 0 (
    echo ERROR: API build failed
    cd /d "%ROOT_DIR%"
    pause
    exit /b %ERRORLEVEL%
)

set JAR_FILE=build\libs\aforclinic-api-0.0.1-SNAPSHOT.jar
if not exist "%JAR_FILE%" (
    echo ERROR: JAR file not found: %JAR_FILE%
    cd /d "%ROOT_DIR%"
    pause
    exit /b 1
)

echo Copying JAR file...
copy /Y "%JAR_FILE%" "%ROOT_DIR%%OUTPUT_DIR%\api\aforclinic-api-0.0.1-SNAPSHOT.jar" >nul
echo API build completed
echo;

cd /d "%ROOT_DIR%"

REM ========================================
REM 2. Web Build
REM ========================================
echo Building Web - Step 2 of 3

if not exist "%WEB_DIR%" (
    echo ERROR: Web directory not found
    echo Path: %WEB_DIR%
    pause
    exit /b 1
)

cd /d "%WEB_DIR%"

where node >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo ERROR: Node.js is not installed.
    cd /d "%ROOT_DIR%"
    pause
    exit /b 1
)

where pnpm >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo pnpm is not installed. Installing...
    call npm install -g pnpm
    if %ERRORLEVEL% neq 0 (
        echo ERROR: pnpm installation failed
        cd /d "%ROOT_DIR%"
        pause
        exit /b 1
    )
)

if not exist "node_modules" (
    echo Installing dependencies...
    call pnpm install
    if %ERRORLEVEL% neq 0 (
        echo ERROR: Dependency installation failed
        cd /d "%ROOT_DIR%"
        pause
        exit /b %ERRORLEVEL%
    )
)

echo Building Nuxt application...
set NODE_ENV=production
call pnpm build
if %ERRORLEVEL% neq 0 (
    echo ERROR: Web build failed
    cd /d "%ROOT_DIR%"
    pause
    exit /b %ERRORLEVEL%
)

if not exist ".output" (
    echo ERROR: .output folder not found.
    cd /d "%ROOT_DIR%"
    pause
    exit /b 1
)

echo Copying .output folder...
xcopy /E /I /Y ".output" "%ROOT_DIR%%OUTPUT_DIR%\web\.output" >nul
echo Web build completed
echo;

cd /d "%ROOT_DIR%"

REM ========================================
REM 3. Admin Build
REM ========================================
echo Building Admin - Step 3 of 3

if not exist "%ADMIN_DIR%" (
    echo ERROR: Admin directory not found
    echo Path: %ADMIN_DIR%
    pause
    exit /b 1
)

cd /d "%ADMIN_DIR%"

if not exist "node_modules" (
    echo Installing dependencies...
    call pnpm install
    if %ERRORLEVEL% neq 0 (
        echo ERROR: Dependency installation failed
        cd /d "%ROOT_DIR%"
        pause
        exit /b %ERRORLEVEL%
    )
)

echo Building Nuxt application...
set NODE_ENV=production
call pnpm build
if %ERRORLEVEL% neq 0 (
    echo ERROR: Admin build failed
    cd /d "%ROOT_DIR%"
    pause
    exit /b %ERRORLEVEL%
)

if not exist ".output" (
    echo ERROR: .output folder not found.
    cd /d "%ROOT_DIR%"
    pause
    exit /b 1
)

echo Copying .output folder...
xcopy /E /I /Y ".output" "%ROOT_DIR%%OUTPUT_DIR%\admin\.output" >nul
echo Admin build completed
echo;

cd /d "%ROOT_DIR%"

REM ========================================
REM Copy ecosystem.config.cjs
REM ========================================
echo Copying ecosystem.config.cjs...
if exist "ecosystem.config.cjs" (
    copy /Y "ecosystem.config.cjs" "%OUTPUT_DIR%\" >nul
    echo ecosystem.config.cjs copied
) else (
    echo WARNING: ecosystem.config.cjs not found
)
echo;

REM ========================================
REM Build Complete
REM ========================================
echo;
echo ========================================
echo Build completed!
echo ========================================
echo;
echo Build output location: %OUTPUT_DIR%
echo   - API:   %OUTPUT_DIR%\api\aforclinic-api-0.0.1-SNAPSHOT.jar
echo   - Web:   %OUTPUT_DIR%\web\.output
echo   - Admin: %OUTPUT_DIR%\admin\.output
echo   - Config: %OUTPUT_DIR%\ecosystem.config.cjs
echo;
echo Next step: Run package.bat to create deployment archive
echo;
pause
