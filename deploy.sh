#!/bin/bash

# Aforclinic Deployment Script
# Usage: ./deploy.sh [package-file.tar.gz or package-file.zip]

set -e

PACKAGE_FILE=${1:-aforclinic-deploy.tar.gz}
DEPLOY_DIR="/var/www"
BACKUP_DIR="/var/www/backups"

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo "========================================"
echo "AforClinic Deployment Script"
echo "========================================"
echo ""

# Check if package file exists
if [ ! -f "/tmp/$PACKAGE_FILE" ] && [ ! -f "$PACKAGE_FILE" ]; then
    echo -e "${RED}ERROR: Package file not found: $PACKAGE_FILE${NC}"
    echo "Please upload the package file to /tmp/ or current directory"
    exit 1
fi

# Determine package file location
if [ -f "/tmp/$PACKAGE_FILE" ]; then
    PACKAGE_PATH="/tmp/$PACKAGE_FILE"
elif [ -f "$PACKAGE_FILE" ]; then
    PACKAGE_PATH="$PACKAGE_FILE"
fi

# Create backup directory
mkdir -p "$BACKUP_DIR"

# Backup existing deployment
echo -e "${YELLOW}Creating backup...${NC}"
BACKUP_FILE="$BACKUP_DIR/backup-$(date +%Y%m%d-%H%M%S).tar.gz"
if [ -d "$DEPLOY_DIR/api" ] || [ -d "$DEPLOY_DIR/web" ] || [ -d "$DEPLOY_DIR/admin" ]; then
    cd "$DEPLOY_DIR"
    tar -czf "$BACKUP_FILE" api web admin ecosystem.config.cjs 2>/dev/null || true
    echo -e "${GREEN}Backup created: $BACKUP_FILE${NC}"
fi

# Stop PM2 processes
echo -e "${YELLOW}Stopping PM2 processes...${NC}"
pm2 stop all 2>/dev/null || true
pm2 delete all 2>/dev/null || true

# Extract package
echo -e "${YELLOW}Extracting package...${NC}"
cd "$DEPLOY_DIR"

if [[ "$PACKAGE_PATH" == *.tar.gz ]]; then
    tar -zxvf "$PACKAGE_PATH" -C "$DEPLOY_DIR"
elif [[ "$PACKAGE_PATH" == *.zip ]]; then
    unzip -q "$PACKAGE_PATH" -d "$DEPLOY_DIR"
else
    echo -e "${RED}ERROR: Unsupported package format${NC}"
    exit 1
fi

# Set permissions
echo -e "${YELLOW}Setting permissions...${NC}"
chown -R ubuntu:ubuntu "$DEPLOY_DIR/api" "$DEPLOY_DIR/web" "$DEPLOY_DIR/admin" 2>/dev/null || true
chmod +x "$DEPLOY_DIR/api"/*.jar 2>/dev/null || true

# Start PM2
echo -e "${YELLOW}Starting PM2...${NC}"
cd "$DEPLOY_DIR"
pm2 start ecosystem.config.cjs
pm2 save

echo ""
echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}Deployment completed!${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""
echo "PM2 Status:"
pm2 status
echo ""
echo "To view logs:"
echo "  pm2 logs api"
echo "  pm2 logs web"
echo "  pm2 logs admin"
echo ""


