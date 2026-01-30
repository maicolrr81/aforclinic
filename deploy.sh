#!/bin/bash

# AforClinic 소스 다운로드 스크립트
# GitHub에서 전체 저장소를 다운받습니다
# 사용법: bash deploy.sh [브랜치명]
# 예시: bash deploy.sh main

# 설정
GIT_REPO="https://github.com/maicolrr81/aforclinic.git"
GIT_BRANCH=${1:-main}
CURRENT_DIR=$(pwd)

# GitHub 인증 정보 (환경변수 또는 직접 설정)
# 방법 1: 환경변수 사용 (권장)
# export GIT_USERNAME="maicolrr81"
# export GIT_TOKEN="your_personal_access_token"
# 
# 방법 2: 직접 설정 (보안상 권장하지 않음)
# 아래에 Personal Access Token을 입력하세요 (패스워드가 아닌 토큰!)
GIT_USERNAME="${GIT_USERNAME:-maicolrr81}"
GIT_TOKEN="${GIT_TOKEN:-}"

# 토큰이 있으면 URL에 포함
if [ -n "$GIT_TOKEN" ]; then
    GIT_REPO_AUTH="https://${GIT_USERNAME}:${GIT_TOKEN}@github.com/maicolrr81/aforclinic.git"
    echo "⚠️  Personal Access Token을 사용합니다."
else
    GIT_REPO_AUTH="$GIT_REPO"
    echo "⚠️  인증 없이 클론 시도 (Public 저장소인 경우에만 작동합니다)"
fi

echo "=========================================="
echo "AforClinic 소스 다운로드"
echo "=========================================="
echo ""
echo "저장소: $GIT_REPO"
echo "브랜치: $GIT_BRANCH"
echo "다운로드 경로: $CURRENT_DIR"
echo ""

# Git 설치 확인
if ! command -v git &> /dev/null; then
    echo "❌ 오류: Git이 설치되어 있지 않습니다."
    echo ""
    echo "Git 설치 방법:"
    echo "  CentOS/RHEL: sudo yum install git"
    echo "  Ubuntu/Debian: sudo apt-get install git"
    echo "  또는: sudo yum install git-core"
    exit 1
fi

# Git 클론 또는 업데이트
if [ -d ".git" ]; then
    echo "기존 저장소 업데이트 중 (pull)..."
    if git fetch origin; then
        if git checkout "$GIT_BRANCH" 2>/dev/null || git checkout -b "$GIT_BRANCH" origin/"$GIT_BRANCH" 2>/dev/null; then
            if git pull origin "$GIT_BRANCH"; then
                echo "✅ 코드 업데이트 완료"
            else
                echo "❌ 오류: git pull 실패"
                exit 1
            fi
        else
            echo "❌ 오류: git checkout 실패"
            exit 1
        fi
    else
        echo "❌ 오류: git fetch 실패"
        exit 1
    fi
else
    echo "새로 클론 중..."
    # 현재 디렉토리에 파일이 있으면 임시 디렉토리에 클론 후 이동
    if [ "$(ls -A . 2>/dev/null)" ]; then
        TEMP_DIR="../aforclinic-temp-$$"
        echo "임시 디렉토리에 클론 중: $TEMP_DIR"
        if git clone -b "$GIT_BRANCH" "$GIT_REPO_AUTH" "$TEMP_DIR"; then
            echo "클론된 파일을 현재 디렉토리로 이동 중..."
            mv "$TEMP_DIR"/* . 2>/dev/null || true
            mv "$TEMP_DIR"/.[^.]* . 2>/dev/null || true
            rm -rf "$TEMP_DIR"
            echo "✅ 코드 다운로드 완료"
        else
            echo "❌ 오류: git clone 실패"
            rm -rf "$TEMP_DIR" 2>/dev/null || true
            exit 1
        fi
    else
        # 디렉토리가 비어있으면 직접 클론
        if git clone -b "$GIT_BRANCH" "$GIT_REPO_AUTH" .; then
            echo "✅ 코드 다운로드 완료"
        else
            echo "❌ 오류: git clone 실패"
            echo "   저장소 URL과 브랜치명을 확인해주세요."
            exit 1
        fi
    fi
fi

echo ""
echo "=========================================="
echo "✅ 다운로드 완료!"
echo "=========================================="
echo ""
