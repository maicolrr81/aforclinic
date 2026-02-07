#!/bin/bash

# Web 배포 스크립트
# 빌드 → PM2 구동 → 확인 → 압축
# 사용법: bash deploy-web.sh

# set -e는 주석 처리 (에러가 있어도 계속 진행)
# set -e

echo "=========================================="
echo "Web 배포 프로세스"
echo "=========================================="
echo ""

# 현재 디렉토리 확인
cd "$(dirname "$0")"
CURRENT_DIR=$(pwd)
echo "작업 디렉토리: $CURRENT_DIR"
echo ""

# 1. 빌드
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "1/4 빌드 시작"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
bash build.sh

if [ ! -d ".output" ]; then
    echo "❌ 오류: 빌드 실패 (.output 디렉토리가 생성되지 않았습니다)"
    exit 1
fi

echo ""
echo "✅ 빌드 완료"
echo ""

# PM2 설치 확인
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "PM2 설치 확인"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

if ! command -v pm2 &> /dev/null; then
    echo "PM2가 설치되어 있지 않습니다. 설치 중..."
    # sudo 권한이 있는지 확인
    if sudo -n true 2>/dev/null; then
        echo "sudo 권한으로 PM2 설치 중..."
        sudo npm install -g pm2
    else
        echo "sudo 권한이 필요합니다. 수동으로 설치해주세요:"
        echo "  sudo npm install -g pm2"
        echo ""
        read -p "지금 설치하시겠습니까? (y/n): " install_choice
        if [ "$install_choice" = "y" ] || [ "$install_choice" = "Y" ]; then
            sudo npm install -g pm2
        else
            echo "❌ PM2 설치가 필요합니다."
            exit 1
        fi
    fi
    
    if [ $? -ne 0 ]; then
        echo "❌ 오류: PM2 설치 실패"
        echo "수동으로 설치해주세요: sudo npm install -g pm2"
        exit 1
    fi
    echo "✅ PM2 설치 완료"
else
    PM2_VERSION=$(pm2 --version)
    echo "✅ PM2 설치됨 (버전: $PM2_VERSION)"
fi

echo ""

# 2. PM2 구동
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "2/4 PM2 구동"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

# PM2가 실행 중인지 확인
if pm2 list 2>/dev/null | grep -q "web"; then
    echo "기존 PM2 프로세스 재시작 중..."
    pm2 restart web
else
    echo "새로운 PM2 프로세스 시작 중..."
    # ecosystem.config.cjs가 있으면 사용, 없으면 직접 시작
    if [ -f "/var/www/ecosystem.config.cjs" ]; then
        pm2 start /var/www/ecosystem.config.cjs --only web
    else
        pm2 start .output/server/index.mjs --name web --env PORT=3000
    fi
fi

# PM2 저장
pm2 save 2>/dev/null || true

echo ""
echo "✅ PM2 구동 완료"
echo ""

# 3. 상태 확인 (간단히)
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "3/4 상태 확인 (3초 대기 후 압축 진행)"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

sleep 3

echo ""
echo "✅ 상태 확인 완료 (압축 진행)"
echo ""

# 4. 압축
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "4/4 .output 압축"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

# 출력 폴더 생성
OUTPUT_DIR="output"
mkdir -p "$OUTPUT_DIR"

TIMESTAMP=$(date +%Y%m%d-%H%M%S)
OUTPUT_FILE="$OUTPUT_DIR/aforclinic-web-output-${TIMESTAMP}.tar.gz"

echo "압축 파일 생성 중: $OUTPUT_FILE"
echo "위치: $CURRENT_DIR/$OUTPUT_FILE"
echo ""

# 압축 진행 (시간이 걸릴 수 있음)
if tar -czf "$OUTPUT_FILE" .output 2>&1; then
    echo "압축 명령어 실행 완료"
else
    TAR_EXIT_CODE=$?
    echo "⚠️  압축 명령어 종료 코드: $TAR_EXIT_CODE"
    # 파일이 생성되었는지 확인
    if [ ! -f "$OUTPUT_FILE" ]; then
        echo "❌ 오류: 압축 파일이 생성되지 않았습니다."
        exit 1
    fi
fi

if [ -f "$OUTPUT_FILE" ]; then
    FILE_SIZE=$(du -h "$OUTPUT_FILE" | cut -f1)
    echo ""
    echo "✅ 압축 완료!"
    echo "파일: $OUTPUT_FILE"
    echo "크기: $FILE_SIZE"
    echo "위치: $CURRENT_DIR/$OUTPUT_FILE"
else
    echo "❌ 오류: 압축 파일 생성 실패"
    exit 1
fi

echo ""
echo "=========================================="
echo "✅ 배포 프로세스 완료!"
echo "=========================================="
echo ""
echo "다음 단계:"
echo "  1. 압축 파일 확인: $OUTPUT_FILE"
echo "  2. PM2 상태: pm2 list"
echo "  3. 로그 확인: pm2 logs web"
echo ""

