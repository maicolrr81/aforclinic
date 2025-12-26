@echo off
chcp 65001 >nul
echo ========================================
echo Web 서버 개발 모드 실행
echo ========================================
echo.

cd /d "%~dp0"

echo 현재 디렉토리: %CD%
echo.

REM Node.js가 설치되어 있는지 확인
where node >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo.
    echo [오류] Node.js가 설치되어 있지 않습니다.
    echo.
    echo Node.js 설치 방법:
    echo 1. https://nodejs.org 에서 Node.js LTS 버전 다운로드 및 설치
    echo 2. 설치 후 PowerShell을 재시작하세요.
    echo.
    pause
    exit /b 1
)

REM npm이 설치되어 있는지 확인
where npm >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo.
    echo [오류] npm이 설치되어 있지 않습니다.
    echo Node.js를 설치하면 npm이 함께 설치됩니다.
    echo.
    pause
    exit /b 1
)

REM pnpm이 설치되어 있는지 확인
where pnpm >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo.
    echo [알림] pnpm이 설치되어 있지 않습니다.
    echo pnpm을 설치합니다...
    echo.
    call npm install -g pnpm
    if %ERRORLEVEL% neq 0 (
        echo.
        echo [오류] pnpm 설치에 실패했습니다.
        echo 수동으로 설치하려면: npm install -g pnpm
        echo.
        pause
        exit /b 1
    )
    echo pnpm 설치 완료!
    echo.
)

REM 의존성 설치 확인
REM pnpm은 .pnpm 폴더를 사용하므로 node_modules만으로는 확인이 어려움
REM package.json이 있으면 설치 시도
if not exist "pnpm-lock.yaml" (
    echo 의존성을 설치합니다...
    call pnpm install
    if %ERRORLEVEL% neq 0 (
        echo 의존성 설치 중 오류가 발생했습니다.
        pause
        exit /b %ERRORLEVEL%
    )
    echo.
) else (
    REM pnpm-lock.yaml이 있지만 node_modules/nuxt가 없으면 재설치
    if not exist "node_modules\nuxt" (
        echo 의존성을 재설치합니다...
        echo (Windows 심볼릭 링크 문제 해결을 위해 node-linker=hoisted 설정 적용)
        echo.
        call pnpm install
        if %ERRORLEVEL% neq 0 (
            echo 의존성 설치 중 오류가 발생했습니다.
            pause
            exit /b %ERRORLEVEL%
        )
        echo.
    )
)

echo Nuxt.js 개발 서버를 시작합니다...
echo 포트: 3000
echo API 서버: http://localhost:9090
echo.

REM 전역 변수 초기화
set GLOBAL_EXIT_CODE=0

REM nuxi 실행 파일 찾기
REM pnpm은 임시 폴더를 사용하므로 동적으로 찾아야 함
set NUXI_PATH=

REM 1. node_modules/.bin/nuxi.cmd 확인 (Windows)
if exist "node_modules\.bin\nuxi.cmd" (
    set NUXI_PATH=node_modules\.bin\nuxi.cmd
    goto :found
)

REM 2. node_modules/.bin/nuxi 확인 (Unix)
if exist "node_modules\.bin\nuxi" (
    set NUXI_PATH=node_modules\.bin\nuxi
    goto :found
)

REM 3. @nuxt/cli 패키지에서 nuxi.mjs 찾기 (pnpm 임시 폴더 구조)
REM PowerShell을 사용하여 파일 찾기 (한글 경로 문제 해결)
for /f "delims=" %%f in ('powershell -Command "Get-ChildItem -Path 'node_modules\@nuxt' -Recurse -Filter 'nuxi.mjs' -ErrorAction SilentlyContinue | Select-Object -First 1 -ExpandProperty FullName"') do (
    set NUXI_PATH=%%f
    goto :found
)

REM 4. node_modules/nuxt/bin/nuxi.mjs 확인
if exist "node_modules\nuxt\bin\nuxi.mjs" (
    set NUXI_PATH=node_modules\nuxt\bin\nuxi.mjs
    goto :found
)

REM 5. nuxt_tmp_* 폴더에서 찾기
for /d %%d in ("node_modules\nuxt_tmp_*") do (
    if exist "%%d\bin\nuxi.mjs" (
        set NUXI_PATH=%%d\bin\nuxi.mjs
        goto :found
    )
)

:found
if defined NUXI_PATH (
    echo nuxi 실행 파일을 찾았습니다: %NUXI_PATH%
    echo.
    REM 한글 경로 문제 해결: 절대 경로로 변환
    setlocal enabledelayedexpansion
    set "SCRIPT_DIR=%~dp0"
    REM NUXI_PATH가 절대 경로인지 확인 (드라이브 문자나 \로 시작하는지)
    echo %NUXI_PATH% | findstr /r "^[A-Za-z]:" >nul
    if !ERRORLEVEL! equ 0 (
        REM 절대 경로인 경우 그대로 사용
        set "FULL_PATH=!NUXI_PATH!"
    ) else (
        REM 상대 경로인 경우 SCRIPT_DIR과 결합
        set "FULL_PATH=!SCRIPT_DIR!!NUXI_PATH!"
    )
    REM 경로의 백슬래시를 정규화
    set "FULL_PATH=!FULL_PATH:\=/!"
    set "FULL_PATH=!FULL_PATH:/=\!"
    echo 실행 경로: !FULL_PATH!
    echo.
    REM Node.js 환경 변수 설정
    set "NODE_OPTIONS=--max-old-space-size=4096"
    set "NODE_ENV=development"
    REM 절대 경로로 실행 (한글 경로 문제 해결)
    call node "!FULL_PATH!" dev
    set "EXIT_CODE=!ERRORLEVEL!"
    REM endlocal 전에 전역 변수로 저장
    for /f "delims=" %%a in ("!EXIT_CODE!") do (
        endlocal
        set "GLOBAL_EXIT_CODE=%%a"
    )
    if not "%GLOBAL_EXIT_CODE%"=="0" (
        echo.
        echo 오류가 발생했습니다.
        pause
        exit /b %GLOBAL_EXIT_CODE%
    )
    goto :end
) else (
    echo [오류] nuxi 실행 파일을 찾을 수 없습니다.
    echo.
    echo 해결 방법:
    echo 1. PowerShell에서 다음 명령어 실행:
    echo    cd aforclinic-web-main\aforclinic-web-main
    echo    pnpm install
    echo.
    echo 2. 설치가 완료될 때까지 기다린 후 다시 실행하세요.
    echo.
    pause
    exit /b 1
)

:end
pause

