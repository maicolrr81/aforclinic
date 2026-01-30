#!/bin/bash

# 배포 스크립트
# 사용법: bash deploy.sh [브랜치명]
# 예시: bash deploy.sh main

set -e  # 에러 발생 시 스크립트 중단

# 설정
GIT_REPO="https://github.com/maicolrr81/aforclinic.git"
GIT_BRANCH=${1:-main}  # 기본값: main
DEPLOY_DIR="/var/www/web"
GIT_CLONE_DIR="/tmp/aforclinic-web-clone"

echo "🚀 배포 시작..."
echo "=========================================="
echo "저장소: $GIT_REPO"
echo "브랜치: $GIT_BRANCH"
echo "배포 경로: $DEPLOY_DIR"
echo "=========================================="
echo ""

# 작업 디렉토리로 이동
cd "$DEPLOY_DIR"

# 현재 시간 저장 (백업 파일명용)
BACKUP_DATE=$(date +%Y%m%d-%H%M%S)

# 기존 파일 백업 (선택사항)
if [ -d "app" ] || [ -d ".output" ]; then
    echo "📦 기존 파일 백업 중..."
    mkdir -p backups
    tar -czf "backups/backup-${BACKUP_DATE}.tar.gz" \
        app public server shared .output .nuxt \
        nuxt.config.ts package.json pnpm-lock.yaml \
        tsconfig.json tailwind.config.ts eslint.config.mjs \
        2>/dev/null || true
    echo "✅ 백업 완료: backups/backup-${BACKUP_DATE}.tar.gz"
fi

# Git 클론 또는 업데이트
echo "📥 GitHub에서 코드 다운로드 중..."
if [ -d "$GIT_CLONE_DIR" ]; then
    echo "기존 클론 디렉토리 업데이트 중..."
    cd "$GIT_CLONE_DIR"
    git fetch origin
    git checkout "$GIT_BRANCH"
    git pull origin "$GIT_BRANCH"
else
    echo "새로 클론 중..."
    rm -rf "$GIT_CLONE_DIR"
    git clone -b "$GIT_BRANCH" "$GIT_REPO" "$GIT_CLONE_DIR"
    cd "$GIT_CLONE_DIR"
fi

# 저장소 구조 확인 및 web 디렉토리 찾기
WEB_SOURCE_DIR=""
if [ -d "aforclinic-web-main/aforclinic-web-main" ]; then
    WEB_SOURCE_DIR="aforclinic-web-main/aforclinic-web-main"
elif [ -d "web" ]; then
    WEB_SOURCE_DIR="web"
elif [ -d "aforclinic-web-main" ]; then
    WEB_SOURCE_DIR="aforclinic-web-main"
elif [ -f "package.json" ] && [ -d "app" ]; then
    WEB_SOURCE_DIR="."
else
    echo "❌ 오류: web 프로젝트 디렉토리를 찾을 수 없습니다."
    echo "   저장소 구조를 확인해주세요."
    exit 1
fi

echo "✅ 소스 디렉토리: $WEB_SOURCE_DIR"

# 소스 파일 복사
echo "📂 소스 파일 복사 중..."
cd "$WEB_SOURCE_DIR"

# 필수 파일 확인
if [ ! -d "app" ] || [ ! -d "public" ]; then
    echo "❌ 오류: 필수 디렉토리(app, public)를 찾을 수 없습니다."
    exit 1
fi

# 배포 디렉토리로 파일 복사
echo "파일 복사 중..."
if command -v rsync &> /dev/null; then
    rsync -av --delete \
        --exclude='.git' \
        --exclude='node_modules' \
        --exclude='.output' \
        --exclude='.nuxt' \
        --exclude='.data' \
        --exclude='.nitro' \
        --exclude='.cache' \
        --exclude='dist' \
        --exclude='*.log' \
        --exclude='.env' \
        --exclude='.env.*' \
        . "$DEPLOY_DIR/"
else
    # rsync가 없으면 cp 사용 (기존 파일 삭제 후 복사)
    echo "rsync를 사용할 수 없어 cp로 복사합니다..."
    cd "$DEPLOY_DIR"
    rm -rf app public server shared nuxt.config.ts package.json pnpm-lock.yaml tsconfig.json tailwind.config.ts eslint.config.mjs
    cd "$GIT_CLONE_DIR/$WEB_SOURCE_DIR"
    cp -r app public server shared nuxt.config.ts package.json pnpm-lock.yaml tsconfig.json tailwind.config.ts eslint.config.mjs "$DEPLOY_DIR/" 2>/dev/null || true
fi

cd "$DEPLOY_DIR"

# 의존성 설치
echo "📥 의존성 설치 중..."
pnpm install

# 빌드
echo "🔨 빌드 중..."
pnpm build

# 빌드 결과 확인
if [ ! -d ".output" ]; then
    echo "❌ 오류: 빌드 실패 (.output 디렉토리가 생성되지 않았습니다)"
    exit 1
fi

# PM2 재시작
echo "🔄 PM2 서버 재시작 중..."
pm2 restart web

# 재시작 실패 시 시작 시도
if [ $? -ne 0 ]; then
    echo "⚠️  PM2 재시작 실패, 새로 시작 시도..."
    pm2 start ecosystem.config.cjs --only web || pm2 start .output/server/index.mjs --name web
fi

# 상태 확인
echo ""
echo "=========================================="
echo "✅ 배포 완료!"
echo ""
echo "📊 PM2 상태:"
pm2 list
echo ""
echo "📋 최근 로그 (마지막 20줄):"
pm2 logs web --lines 20 --nostream
echo ""
echo "💡 전체 로그 보기: pm2 logs web"
echo "💡 실시간 로그 보기: pm2 logs web --lines 50"

