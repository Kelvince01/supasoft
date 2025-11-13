#!/bin/bash

################################################################################
# Supasoft Database Backup Script
# 
# Description: Automated backup script for all Supasoft databases
# Usage: ./backup-database.sh [OPTIONS]
# 
# Options:
#   -d, --destination DIR    Backup destination directory (default: ./backups)
#   -r, --retention DAYS     Number of days to retain backups (default: 30)
#   -c, --compress          Compress backups using gzip
#   -h, --help              Display this help message
################################################################################

set -e  # Exit on error
set -u  # Exit on undefined variable

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Default configuration
BACKUP_DIR="./backups"
RETENTION_DAYS=30
COMPRESS=true
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
DATE=$(date +%Y-%m-%d)

# Database configuration (override with environment variables)
DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-3306}"
DB_USER="${DB_USER:-supasoft_app}"
DB_PASSWORD="${DB_PASSWORD:-}"

# List of databases to backup
DATABASES=(
    "supasoft_auth"
    "supasoft_items"
    "supasoft_pricing"
    "supasoft_stock"
    "supasoft_sales"
    "supasoft_purchase"
    "supasoft_transfer"
    "supasoft_reports"
    "supasoft_notifications"
    "supasoft_integrations"
    "supasoft_audit"
)

# Logging
LOG_FILE="${BACKUP_DIR}/backup_${TIMESTAMP}.log"

################################################################################
# Functions
################################################################################

log() {
    echo -e "${GREEN}[$(date +'%Y-%m-%d %H:%M:%S')]${NC} $1" | tee -a "$LOG_FILE"
}

error() {
    echo -e "${RED}[ERROR]${NC} $1" | tee -a "$LOG_FILE"
    exit 1
}

warn() {
    echo -e "${YELLOW}[WARNING]${NC} $1" | tee -a "$LOG_FILE"
}

show_help() {
    cat << EOF
Supasoft Database Backup Script

Usage: $0 [OPTIONS]

Options:
    -d, --destination DIR    Backup destination directory (default: ./backups)
    -r, --retention DAYS     Number of days to retain backups (default: 30)
    -c, --compress          Compress backups using gzip (default: enabled)
    -n, --no-compress       Do not compress backups
    -h, --help              Display this help message

Environment Variables:
    DB_HOST                 Database host (default: localhost)
    DB_PORT                 Database port (default: 3306)
    DB_USER                 Database username (default: supasoft_app)
    DB_PASSWORD             Database password (required)

Example:
    # Basic backup
    export DB_PASSWORD='your_password'
    $0

    # Custom destination and retention
    $0 -d /mnt/backups -r 60

    # Backup without compression
    $0 -n

EOF
    exit 0
}

check_requirements() {
    log "Checking requirements..."
    
    # Check if mysqldump is installed
    if ! command -v mysqldump &> /dev/null; then
        error "mysqldump is not installed. Please install MySQL client."
    fi
    
    # Check if gzip is installed (if compression enabled)
    if [ "$COMPRESS" = true ] && ! command -v gzip &> /dev/null; then
        error "gzip is not installed. Please install gzip or use --no-compress option."
    fi
    
    # Check if database password is provided
    if [ -z "$DB_PASSWORD" ]; then
        error "Database password not provided. Set DB_PASSWORD environment variable."
    fi
    
    log "Requirements check passed."
}

create_backup_directory() {
    log "Creating backup directory: $BACKUP_DIR"
    
    mkdir -p "$BACKUP_DIR"
    
    if [ ! -w "$BACKUP_DIR" ]; then
        error "Backup directory is not writable: $BACKUP_DIR"
    fi
    
    log "Backup directory ready."
}

test_database_connection() {
    log "Testing database connection..."
    
    if ! mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" -e "SELECT 1;" &> /dev/null; then
        error "Cannot connect to database. Check credentials and connection."
    fi
    
    log "Database connection successful."
}

backup_database() {
    local db_name=$1
    local backup_file="${BACKUP_DIR}/${db_name}_${TIMESTAMP}.sql"
    
    log "Backing up database: $db_name"
    
    # Check if database exists
    if ! mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASSWORD" -e "USE $db_name;" &> /dev/null; then
        warn "Database $db_name does not exist. Skipping..."
        return
    fi
    
    # Perform backup
    mysqldump -h "$DB_HOST" \
              -P "$DB_PORT" \
              -u "$DB_USER" \
              -p"$DB_PASSWORD" \
              --single-transaction \
              --routines \
              --triggers \
              --events \
              --add-drop-database \
              --databases "$db_name" > "$backup_file"
    
    if [ $? -eq 0 ]; then
        log "✓ Backup successful: $backup_file"
        
        # Compress if enabled
        if [ "$COMPRESS" = true ]; then
            log "Compressing backup..."
            gzip "$backup_file"
            backup_file="${backup_file}.gz"
            log "✓ Compression complete: $backup_file"
        fi
        
        # Calculate and display file size
        local size=$(du -h "$backup_file" | cut -f1)
        log "Backup size: $size"
    else
        error "Failed to backup database: $db_name"
    fi
}

backup_all_databases() {
    log "Starting backup of all databases..."
    
    local total=${#DATABASES[@]}
    local current=0
    
    for db in "${DATABASES[@]}"; do
        current=$((current + 1))
        log "[$current/$total] Processing $db..."
        backup_database "$db"
    done
    
    log "All databases backed up successfully."
}

cleanup_old_backups() {
    log "Cleaning up old backups (retention: $RETENTION_DAYS days)..."
    
    local deleted_count=0
    
    # Find and delete old backups
    if [ "$COMPRESS" = true ]; then
        deleted_count=$(find "$BACKUP_DIR" -name "*.sql.gz" -type f -mtime +$RETENTION_DAYS -delete -print | wc -l)
    else
        deleted_count=$(find "$BACKUP_DIR" -name "*.sql" -type f -mtime +$RETENTION_DAYS -delete -print | wc -l)
    fi
    
    if [ "$deleted_count" -gt 0 ]; then
        log "Deleted $deleted_count old backup(s)."
    else
        log "No old backups to delete."
    fi
}

generate_backup_report() {
    log "Generating backup report..."
    
    local report_file="${BACKUP_DIR}/backup_report_${DATE}.txt"
    
    cat > "$report_file" << EOF
===============================================================================
Supasoft Database Backup Report
===============================================================================
Date: $(date)
Host: $DB_HOST:$DB_PORT
User: $DB_USER
Backup Directory: $BACKUP_DIR
Retention: $RETENTION_DAYS days
Compression: $([ "$COMPRESS" = true ] && echo "Enabled" || echo "Disabled")

Databases Backed Up:
-------------------------------------------------------------------------------
EOF
    
    for db in "${DATABASES[@]}"; do
        if [ "$COMPRESS" = true ]; then
            local backup_file="${BACKUP_DIR}/${db}_${TIMESTAMP}.sql.gz"
        else
            local backup_file="${BACKUP_DIR}/${db}_${TIMESTAMP}.sql"
        fi
        
        if [ -f "$backup_file" ]; then
            local size=$(du -h "$backup_file" | cut -f1)
            echo "  ✓ $db - $size" >> "$report_file"
        fi
    done
    
    cat >> "$report_file" << EOF

Backup Summary:
-------------------------------------------------------------------------------
Total Backups: ${#DATABASES[@]}
Total Size: $(du -sh "$BACKUP_DIR" | cut -f1)

===============================================================================
EOF
    
    cat "$report_file"
    log "Report saved to: $report_file"
}

################################################################################
# Main execution
################################################################################

main() {
    # Parse command line arguments
    while [[ $# -gt 0 ]]; do
        case $1 in
            -d|--destination)
                BACKUP_DIR="$2"
                shift 2
                ;;
            -r|--retention)
                RETENTION_DAYS="$2"
                shift 2
                ;;
            -c|--compress)
                COMPRESS=true
                shift
                ;;
            -n|--no-compress)
                COMPRESS=false
                shift
                ;;
            -h|--help)
                show_help
                ;;
            *)
                error "Unknown option: $1. Use -h for help."
                ;;
        esac
    done
    
    log "=========================================="
    log "Supasoft Database Backup Script"
    log "=========================================="
    log "Timestamp: $TIMESTAMP"
    log "Backup Directory: $BACKUP_DIR"
    log "Retention: $RETENTION_DAYS days"
    log "Compression: $([ "$COMPRESS" = true ] && echo "Enabled" || echo "Disabled")"
    log "=========================================="
    
    # Execute backup process
    check_requirements
    create_backup_directory
    test_database_connection
    backup_all_databases
    cleanup_old_backups
    generate_backup_report
    
    log "=========================================="
    log "Backup completed successfully!"
    log "=========================================="
}

# Run main function
main "$@"
