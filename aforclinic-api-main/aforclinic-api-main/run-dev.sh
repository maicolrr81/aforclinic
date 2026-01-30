#!/bin/bash

# API Server Development Mode (Linux)
# 사용법: bash run-dev.sh

echo "========================================"
echo "API Server Development Mode"
echo "========================================"
echo ""

# 현재 디렉토리로 이동
cd "$(dirname "$0")"
echo "Current directory: $(pwd)"
echo ""

# Java 확인
if ! command -v java &> /dev/null; then
    echo "❌ 오류: Java가 설치되어 있지 않습니다."
    echo "Java 21 (Amazon Corretto)를 설치해주세요."
    exit 1
fi

echo "Java version:"
java -version
echo ""

# Gradle 권한 확인
if [ ! -x "gradlew" ]; then
    chmod +x gradlew
fi

# JAR 파일 빌드
echo "Building JAR file..."
./gradlew clean build -x test

if [ $? -ne 0 ]; then
    echo ""
    echo "❌ 오류: 빌드 실패"
    exit 1
fi

echo ""
echo "Build completed successfully!"
echo ""

# JAR 파일 확인
JAR_FILE="build/libs/aforclinic-api-0.0.1-SNAPSHOT.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo "❌ 오류: JAR 파일을 찾을 수 없습니다: $JAR_FILE"
    exit 1
fi

echo "========================================"
echo "Starting Spring Boot Application"
echo "========================================"
echo "Profile: local (development)"
echo "Port: 9090"
echo "API Base: http://localhost:9090"
echo ""
echo "Press Ctrl+C to stop the server"
echo ""

# JAR 파일 실행
java -jar "$JAR_FILE" --spring.profiles.active=local

if [ $? -ne 0 ]; then
    echo ""
    echo "❌ 오류: 애플리케이션 시작 실패"
    exit 1
fi

