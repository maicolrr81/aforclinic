@echo off
chcp 65001 >nul

echo ========================================
echo Admin Server Development Mode
echo ========================================
echo.

cd /d "%~dp0"

echo Current directory: %CD%
echo.

REM Check Node.js
where node >nul 2>&1
if errorlevel 1 (
    echo ERROR: Node.js is not installed.
    echo Please install Node.js LTS from https://nodejs.org
    pause
    exit /b 1
)

REM Check npm
where npm >nul 2>&1
if errorlevel 1 (
    echo ERROR: npm is not installed.
    echo npm comes with Node.js installation.
    pause
    exit /b 1
)

REM Check and install pnpm
where pnpm >nul 2>&1
if errorlevel 1 (
    echo pnpm is not installed. Installing...
    call npm install -g pnpm
    if errorlevel 1 (
        echo ERROR: pnpm installation failed
        echo Please install manually: npm install -g pnpm
        pause
        exit /b 1
    )
)

REM Install dependencies if needed
if not exist "node_modules\nuxt" (
    echo Installing dependencies...
    call pnpm install
    if errorlevel 1 (
        echo ERROR: Dependency installation failed
        pause
        exit /b 1
    )
)

echo ========================================
echo Starting Nuxt.js Development Server
echo ========================================
echo Port: 3001
echo API Server: http://localhost:9090
echo Admin URL: http://localhost:3001
echo.
echo Press Ctrl+C to stop the server
echo.

REM Set environment variables
set "NODE_ENV=development"
set "NODE_OPTIONS=--max-old-space-size=4096"

REM Run development server
call pnpm dev

if errorlevel 1 (
    echo.
    echo ERROR: Failed to start development server
    pause
    exit /b 1
)

pause

