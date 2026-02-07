#!/bin/bash

# Web Server Development Mode (Linux)
# 사용법: bash run-dev.sh

echo "========================================"
echo "Web Server Development Mode"
echo "========================================"
echo ""

# 현재 디렉토리로 이동
cd "$(dirname "$0")"
echo "Current directory: $(pwd)"
echo ""

# Node.js 확인
if ! command -v node &> /dev/null; then
    echo "❌ 오류: Node.js가 설치되어 있지 않습니다."
    echo "Node.js LTS를 설치해주세요: https://nodejs.org"
    exit 1
fi

# npm 확인
if ! command -v npm &> /dev/null; then
    echo "❌ 오류: npm이 설치되어 있지 않습니다."
    echo "npm은 Node.js와 함께 설치됩니다."
    exit 1
fi

# pnpm 확인 및 설치
if ! command -v pnpm &> /dev/null; then
    echo "pnpm이 설치되어 있지 않습니다. 설치 중..."
    npm install -g pnpm
    if [ $? -ne 0 ]; then
        echo "❌ 오류: pnpm 설치 실패"
        echo "수동으로 설치해주세요: npm install -g pnpm"
        exit 1
    fi
fi

# 의존성 설치 확인
if [ ! -d "node_modules/nuxt" ]; then
    echo "Installing dependencies..."
    pnpm install
    if [ $? -ne 0 ]; then
        echo "❌ 오류: 의존성 설치 실패"
        exit 1
    fi
fi

echo "========================================"
echo "Starting Nuxt.js Development Server"
echo "========================================"
echo "Port: 3000"
echo "API Server: http://localhost:9090"
echo "Web URL: http://localhost:3000"
echo ""
echo "⚠️  외부 접근을 위해 --host 옵션 사용"
echo "   서버 IP로 접근: http://서버IP:3000"
echo ""
echo "Press Ctrl+C to stop the server"
echo ""

# 환경변수 설정
export NODE_ENV=development
export NODE_OPTIONS="--max-old-space-size=4096"
export HOST=0.0.0.0
export PORT=3000

# 개발 서버 실행 (외부 접근 허용)
pnpm dev --host 0.0.0.0

if [ $? -ne 0 ]; then
    echo ""
    echo "❌ 오류: 개발 서버 시작 실패"
    exit 1
fi

