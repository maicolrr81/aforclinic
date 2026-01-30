@echo off
chcp 65001 >nul
echo ==========================================
echo 배포용 압축 파일 생성
echo ==========================================
echo.

REM 현재 디렉토리 확인
cd /d "%~dp0"

REM 이전 압축 파일 삭제
if exist aforclinic-web.tar.gz (
    echo 기존 압축 파일 삭제 중...
    del aforclinic-web.tar.gz
)

echo 압축 파일 생성 중...
echo 포함되는 파일/폴더:
echo   - app/
echo   - public/
echo   - server/
echo   - shared/
echo   - nuxt.config.ts
echo   - package.json
echo   - pnpm-lock.yaml
echo   - tsconfig.json
echo   - tailwind.config.ts
echo   - eslint.config.mjs
echo.

REM tar 압축 (Windows 10 이상에서 tar 명령어 사용 가능)
tar -czf aforclinic-web.tar.gz ^
    app ^
    public ^
    server ^
    shared ^
    nuxt.config.ts ^
    package.json ^
    pnpm-lock.yaml ^
    tsconfig.json ^
    tailwind.config.ts ^
    eslint.config.mjs

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ==========================================
    echo ✅ 압축 완료!
    echo ==========================================
    echo.
    echo 파일: aforclinic-web.tar.gz
    for %%A in (aforclinic-web.tar.gz) do echo 크기: %%~zA bytes
    echo.
    echo 다음 단계:
    echo 1. WinSCP 또는 SFTP로 서버(/var/www/web)에 업로드
    echo 2. SSH로 서버 접속
    echo 3. cd /var/www/web
    echo 4. bash deploy.sh 실행
    echo.
) else (
    echo.
    echo ❌ 오류: 압축 실패
    echo tar 명령어가 사용 가능한지 확인해주세요.
    echo (Windows 10 이상에서 기본 제공)
    echo.
)

pause

