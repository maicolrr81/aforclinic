#!/bin/bash

# API 빌드 스크립트
# 사용법: bash build.sh

set -e  # 에러 발생 시 스크립트 중단

echo "🔨 API 빌드 시작..."
echo "=========================================="

# 현재 디렉토리 확인
if [ ! -f "build.gradle" ]; then
    echo "❌ 오류: build.gradle 파일을 찾을 수 없습니다."
    echo "   API 디렉토리에서 실행해주세요."
    exit 1
fi

# Java 확인
if ! command -v java &> /dev/null; then
    echo "❌ 오류: Java가 설치되어 있지 않습니다."
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1)
echo "Java 버전: $JAVA_VERSION"

# Gradle 권한 확인
if [ ! -x "gradlew" ]; then
    chmod +x gradlew
fi

# 빌드
echo "📦 Gradle 빌드 중..."
./gradlew clean build -x test

# 빌드 결과 확인
JAR_FILE="build/libs/aforclinic-api-0.0.1-SNAPSHOT.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo "❌ 오류: JAR 파일이 생성되지 않았습니다: $JAR_FILE"
    exit 1
fi

echo ""
echo "=========================================="
echo "✅ API 빌드 완료!"
echo "=========================================="
echo "JAR 파일: $JAR_FILE"
echo "크기: $(du -h "$JAR_FILE" | cut -f1)"
echo ""

