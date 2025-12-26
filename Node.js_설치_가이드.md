# Node.js 설치 가이드

이 프로젝트를 실행하려면 Node.js와 pnpm이 필요합니다.

## 📦 필수 요구사항

- **Node.js** (LTS 버전 권장)
- **pnpm** (Node.js 설치 후 자동 설치 가능)

---

## 🚀 설치 방법

### 방법 1: 공식 웹사이트에서 설치 (권장)

1. **Node.js 다운로드**
   - https://nodejs.org 접속
   - **LTS (Long Term Support)** 버전 다운로드
   - Windows Installer (.msi) 선택

2. **설치 실행**
   - 다운로드한 설치 파일 실행
   - 기본 설정으로 설치 진행
   - "Add to PATH" 옵션이 체크되어 있는지 확인

3. **설치 확인**
   - PowerShell 또는 명령 프롬프트를 **새로 열기**
   - 다음 명령어 실행:
     ```powershell
     node --version
     npm --version
     ```
   - 버전이 표시되면 설치 완료!

4. **pnpm 설치**
   ```powershell
   npm install -g pnpm
   ```

5. **pnpm 설치 확인**
   ```powershell
   pnpm --version
   ```

---

### 방법 2: Chocolatey 사용 (Windows 패키지 관리자)

1. **Chocolatey 설치** (아직 설치하지 않은 경우)
   - PowerShell을 **관리자 권한**으로 실행
   - 다음 명령어 실행:
     ```powershell
     Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
     ```

2. **Node.js 설치**
   ```powershell
   choco install nodejs-lts
   ```

3. **PowerShell 재시작**

4. **pnpm 설치**
   ```powershell
   npm install -g pnpm
   ```

---

### 방법 3: winget 사용 (Windows 11 또는 최신 Windows 10)

1. **PowerShell 또는 명령 프롬프트 실행**

2. **Node.js 설치**
   ```powershell
   winget install OpenJS.NodeJS.LTS
   ```

3. **PowerShell 재시작**

4. **pnpm 설치**
   ```powershell
   npm install -g pnpm
   ```

---

## ✅ 설치 확인

모든 설치가 완료되었는지 확인:

```powershell
# Node.js 버전 확인
node --version
# 예상 출력: v20.x.x 또는 v22.x.x

# npm 버전 확인
npm --version
# 예상 출력: 10.x.x

# pnpm 버전 확인
pnpm --version
# 예상 출력: 10.x.x
```

---

## 🔧 문제 해결

### 문제 1: "npm이 인식되지 않습니다"

**원인**: Node.js가 설치되지 않았거나 PATH에 등록되지 않음

**해결 방법**:
1. Node.js를 설치했는지 확인
2. PowerShell을 **완전히 종료 후 다시 실행**
3. 여전히 안 되면 시스템 환경 변수 PATH에 Node.js 경로 추가
   - 일반 경로: `C:\Program Files\nodejs\`

### 문제 2: "pnpm이 인식되지 않습니다"

**원인**: pnpm이 전역으로 설치되지 않음

**해결 방법**:
```powershell
npm install -g pnpm
```

설치 후 PowerShell을 재시작하세요.

### 문제 3: 권한 오류

**원인**: 전역 패키지 설치 권한 부족

**해결 방법**:
- PowerShell을 **관리자 권한**으로 실행
- 또는 npm 설정 변경:
  ```powershell
  npm config set prefix %APPDATA%\npm
  ```

### 문제 4: 설치 후에도 명령어가 인식되지 않음

**해결 방법**:
1. PowerShell을 **완전히 종료 후 다시 실행**
2. 시스템 재부팅 (필요한 경우)
3. 환경 변수 PATH 확인:
   ```powershell
   $env:PATH -split ';' | Select-String -Pattern 'node'
   ```

---

## 📝 참고사항

- **Node.js LTS 버전 권장**: 안정성과 장기 지원을 위해 LTS 버전 사용
- **PowerShell 재시작**: 설치 후 반드시 PowerShell을 재시작해야 PATH가 업데이트됩니다
- **프로젝트별 pnpm 버전**: 각 프로젝트는 특정 pnpm 버전을 사용할 수 있습니다
  - Web: `pnpm@10.10.0`
  - Admin: `pnpm@10.5.2`

---

## 🎯 다음 단계

설치가 완료되면:

1. **개발 서버 실행**
   - `run-dev.bat` 파일 실행

2. **프로덕션 빌드**
   - `build-prod.bat` 파일 실행

---

## 📚 추가 리소스

- Node.js 공식 사이트: https://nodejs.org
- pnpm 공식 사이트: https://pnpm.io
- Chocolatey: https://chocolatey.org

