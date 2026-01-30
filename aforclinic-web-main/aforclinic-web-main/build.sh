#!/bin/bash

# Web 빌드 스크립트
# 사용법: bash build.sh

set -e  # 에러 발생 시 스크립트 중단

echo "🔨 Web 빌드 시작..."
echo "=========================================="

# 현재 디렉토리 확인
if [ ! -f "package.json" ] || [ ! -d "app" ]; then
    echo "❌ 오류: package.json 또는 app 디렉토리를 찾을 수 없습니다."
    echo "   Web 디렉토리에서 실행해주세요."
    exit 1
fi

# Node.js 확인
if ! command -v node &> /dev/null; then
    echo "❌ 오류: Node.js가 설치되어 있지 않습니다."
    exit 1
fi

# pnpm 확인 및 설치
if ! command -v pnpm &> /dev/null; then
    echo "📦 pnpm 설치 중..."
    npm install -g pnpm
fi

NODE_VERSION=$(node --version)
PNPM_VERSION=$(pnpm --version)
echo "Node.js 버전: $NODE_VERSION"
echo "pnpm 버전: $PNPM_VERSION"

# 의존성 설치
echo ""
echo "📥 의존성 설치 중..."
pnpm install

# 빌드
echo ""
echo "🔨 Nuxt 빌드 중..."
NODE_ENV=production pnpm build

# 빌드 결과 확인
if [ ! -d ".output" ]; then
    echo "❌ 오류: 빌드 실패 (.output 디렉토리가 생성되지 않았습니다)"
    exit 1
fi

echo ""
echo "=========================================="
echo "✅ Web 빌드 완료!"
echo "=========================================="
echo "빌드 결과: .output/"
echo "크기: $(du -sh .output | cut -f1)"
echo ""

