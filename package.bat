@echo off
chcp 65001 >nul

echo ========================================
echo AforClinic Package Script
echo ========================================
echo.

cd /d "%~dp0"

set OUTPUT_DIR=output
set PACKAGE_NAME=aforclinic-deploy

REM Check if output directory exists
if not exist "%OUTPUT_DIR%" (
    echo ERROR: Output directory not found.
    echo Please run build.bat first.
    pause
    exit /b 1
)

REM Check if required files exist
if not exist "%OUTPUT_DIR%\api\aforclinic-api-0.0.1-SNAPSHOT.jar" (
    echo ERROR: API JAR file not found.
    echo Please run build.bat first.
    pause
    exit /b 1
)

if not exist "%OUTPUT_DIR%\web\.output" (
    echo ERROR: Web .output folder not found.
    echo Please run build.bat first.
    pause
    exit /b 1
)

if not exist "%OUTPUT_DIR%\admin\.output" (
    echo ERROR: Admin .output folder not found.
    echo Please run build.bat first.
    pause
    exit /b 1
)

echo Creating deployment package...
echo.

REM Remove old package if exists
if exist "%PACKAGE_NAME%.tar.gz" del /F /Q "%PACKAGE_NAME%.tar.gz"
if exist "%PACKAGE_NAME%.zip" del /F /Q "%PACKAGE_NAME%.zip"

REM Create package using 7-Zip or PowerShell
where 7z >nul 2>&1
if %ERRORLEVEL% equ 0 (
    echo Using 7-Zip to create tar.gz...
    cd "%OUTPUT_DIR%"
    7z a -ttar "%TEMP%\%PACKAGE_NAME%.tar" * >nul
    7z a -tgzip "%~dp0%PACKAGE_NAME%.tar.gz" "%TEMP%\%PACKAGE_NAME%.tar" >nul
    del "%TEMP%\%PACKAGE_NAME%.tar" >nul
    cd /d "%~dp0"
    echo Package created: %PACKAGE_NAME%.tar.gz
) else (
    echo Using PowerShell to create zip...
    powershell -Command "Compress-Archive -Path '%OUTPUT_DIR%\*' -DestinationPath '%PACKAGE_NAME%.zip' -Force"
    echo Package created: %PACKAGE_NAME%.zip
)

echo.
echo ========================================
echo Package created successfully!
echo ========================================
echo.
echo Package file: %PACKAGE_NAME%.tar.gz or %PACKAGE_NAME%.zip
echo.
echo Next steps:
echo   1. Upload package to server: /tmp/
echo   2. On server, extract: tar -zxvf %PACKAGE_NAME%.tar.gz -C /var/www
echo   3. On server, start: pm2 start /var/www/ecosystem.config.cjs
echo.
pause


