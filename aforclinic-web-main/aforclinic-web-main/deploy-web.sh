#!/bin/bash

# Web 배포 스크립트
# 빌드 → PM2 구동 → 확인 → 압축
# 사용법: bash deploy-web.sh

set -e  # 에러 발생 시 스크립트 중단

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

# 2. PM2 구동
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "2/4 PM2 구동"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

# PM2가 실행 중인지 확인
if pm2 list | grep -q "web"; then
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

# 3. 상태 확인 (대기)
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "3/4 상태 확인 (10초 대기)"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

sleep 10

# PM2 상태 확인
echo ""
echo "PM2 상태:"
pm2 list

# 로그 확인 (에러 체크)
echo ""
echo "최근 로그 확인 (마지막 30줄):"
pm2 logs web --lines 30 --nostream 2>/dev/null || true

# 에러 확인
ERROR_COUNT=$(pm2 logs web --lines 100 --nostream 2>/dev/null | grep -i "error\|failed\|fatal" | wc -l || echo "0")

if [ "$ERROR_COUNT" -gt 0 ]; then
    echo ""
    echo "⚠️  경고: 로그에 에러가 발견되었습니다."
    echo "에러 개수: $ERROR_COUNT"
    echo ""
    read -p "압축을 계속 진행하시겠습니까? (y/n): " continue_choice
    if [ "$continue_choice" != "y" ] && [ "$continue_choice" != "Y" ]; then
        echo "배포 중단"
        exit 1
    fi
fi

# 프로세스 상태 확인
PM2_STATUS=$(pm2 jlist | grep -o '"pm2_env":{"status":"[^"]*"' | grep -o 'status":"[^"]*' | cut -d'"' -f3 || echo "unknown")

if [ "$PM2_STATUS" != "online" ]; then
    echo ""
    echo "❌ 오류: PM2 프로세스가 정상적으로 실행되지 않았습니다."
    echo "상태: $PM2_STATUS"
    echo ""
    echo "로그 확인:"
    pm2 logs web --lines 50
    exit 1
fi

echo ""
echo "✅ 상태 확인 완료 (정상)"
echo ""

# 4. 압축
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "4/4 .output 압축"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

TIMESTAMP=$(date +%Y%m%d-%H%M%S)
OUTPUT_FILE="../aforclinic-web-output-${TIMESTAMP}.tar.gz"

echo "압축 파일 생성 중: $OUTPUT_FILE"
tar -czf "$OUTPUT_FILE" .output

if [ -f "$OUTPUT_FILE" ]; then
    FILE_SIZE=$(du -h "$OUTPUT_FILE" | cut -f1)
    echo ""
    echo "✅ 압축 완료!"
    echo "파일: $OUTPUT_FILE"
    echo "크기: $FILE_SIZE"
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

