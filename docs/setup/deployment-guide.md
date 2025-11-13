# Supasoft - Production Deployment Guide

## 1. DEPLOYMENT OVERVIEW

### 1.1 Deployment Architecture

```
┌─────────────────────────────────────────────────────────┐
│                     Load Balancer                        │
│              (Nginx / AWS ALB / Azure LB)               │
└────────────────────┬────────────────────────────────────┘
                     │
        ┌────────────┴────────────┐
        │                         │
┌───────▼────────┐       ┌───────▼────────┐
│  API Gateway   │       │  API Gateway   │
│   Instance 1   │       │   Instance 2   │
└───────┬────────┘       └───────┬────────┘
        │                         │
        └────────────┬────────────┘
                     │
    ┌────────────────┼────────────────┐
    │                │                │
┌───▼───┐      ┌────▼────┐      ┌───▼───┐
│ Core  │      │ Support │      │ Infra │
│Services│      │Services │      │Services│
└───┬───┘      └────┬────┘      └───┬───┘
    │               │               │
    └───────────────┼───────────────┘
                    │
         ┌──────────┼──────────┐
         │          │          │
    ┌────▼───┐  ┌──▼──┐  ┌────▼────┐
    │ MySQL  │  │Redis│  │RabbitMQ │
    └────────┘  └─────┘  └─────────┘
```

### 1.2 Deployment Options

| Option | Use Case | Complexity | Cost |
|--------|----------|------------|------|
| **Single Server** | Small business, < 50 users | Low | $ |
| **Multi-Server** | Medium business, 50-500 users | Medium | $$ |
| **Cloud (AWS/Azure)** | Large business, 500+ users | High | $$$ |
| **Kubernetes** | Enterprise, scalability required | Very High | $$$$ |

This guide covers **Multi-Server** and **Cloud (AWS)** deployments.

---

## 2. PRE-DEPLOYMENT CHECKLIST

### 2.1 Infrastructure Requirements

#### Minimum Production Servers

| Server Type | Quantity | Specs | Purpose |
|------------|----------|-------|---------|
| **Application Server** | 2 | 8 vCPU, 16 GB RAM, 100 GB SSD | Microservices |
| **Database Server** | 1 | 4 vCPU, 16 GB RAM, 500 GB SSD | MySQL Primary |
| **Database Replica** | 1 | 4 vCPU, 16 GB RAM, 500 GB SSD | MySQL Replica (Read) |
| **Cache Server** | 1 | 2 vCPU, 8 GB RAM, 50 GB SSD | Redis |
| **Message Queue** | 1 | 2 vCPU, 4 GB RAM, 50 GB SSD | RabbitMQ |
| **Load Balancer** | 1 | 2 vCPU, 4 GB RAM, 20 GB SSD | Nginx |

**Total Minimum**: 6 servers

#### Recommended Production Setup

| Server Type | Quantity | Specs | Purpose |
|------------|----------|-------|---------|
| **Application Server** | 3+ | 16 vCPU, 32 GB RAM, 200 GB SSD | Microservices (HA) |
| **Database Server** | 1 | 8 vCPU, 32 GB RAM, 1 TB SSD | MySQL Primary |
| **Database Replica** | 2 | 8 vCPU, 32 GB RAM, 1 TB SSD | MySQL Replicas |
| **Cache Server** | 2 | 4 vCPU, 16 GB RAM, 100 GB SSD | Redis Cluster |
| **Message Queue** | 3 | 4 vCPU, 8 GB RAM, 100 GB SSD | RabbitMQ Cluster |
| **Load Balancer** | 2 | 4 vCPU, 8 GB RAM, 50 GB SSD | Nginx (HA) |
| **Monitoring Server** | 1 | 4 vCPU, 8 GB RAM, 200 GB SSD | Prometheus + Grafana |

**Total Recommended**: 12+ servers

### 2.2 Network Requirements

- **Internet Connection**: Dedicated, minimum 100 Mbps
- **Static IP Addresses**: At least 2 (primary + backup)
- **Domain Name**: Registered (e.g., api.supasoft.com)
- **SSL Certificate**: Valid (Let's Encrypt or commercial)
- **Firewall**: Configured with security groups
- **VPN**: For admin access (recommended)

### 2.3 Software Requirements

| Software | Version | Purpose |
|----------|---------|---------|
| Ubuntu Server | 22.04 LTS | Operating System |
| Java JRE | 21 | Runtime Environment |
| MySQL | 8.0+ | Database |
| Redis | 7.x | Caching |
| RabbitMQ | 3.x | Message Queue |
| Nginx | 1.24+ | Load Balancer / Reverse Proxy |
| Docker | 24.x+ | Containerization (optional) |
| systemd | Latest | Service Management |

### 2.4 Security Checklist

- [ ] Firewall configured (UFW/iptables)
- [ ] SSH key-based authentication enabled
- [ ] Root SSH login disabled
- [ ] Fail2ban installed and configured
- [ ] SSL/TLS certificates obtained
- [ ] Database users created with limited privileges
- [ ] Strong passwords for all services
- [ ] VPN access configured
- [ ] Security updates enabled
- [ ] Intrusion detection system (IDS) installed

---

## 3. DEPLOYMENT STEPS

### 3.1 Server Preparation

#### Step 1: Update System

```bash
# Update package lists
sudo apt update

# Upgrade packages
sudo apt upgrade -y

# Install essential tools
sudo apt install -y curl wget git vim htop net-tools
```

#### Step 2: Configure Firewall

```bash
# Install UFW
sudo apt install ufw -y

# Default policies
sudo ufw default deny incoming
sudo ufw default allow outgoing

# Allow SSH
sudo ufw allow 22/tcp

# Allow HTTP/HTTPS
sudo ufw allow 80/tcp
sudo ufw allow 443/tcp

# Allow internal services (restrict to internal IPs in production)
sudo ufw allow from 10.0.0.0/8 to any port 3306  # MySQL
sudo ufw allow from 10.0.0.0/8 to any port 6379  # Redis
sudo ufw allow from 10.0.0.0/8 to any port 5672  # RabbitMQ
sudo ufw allow from 10.0.0.0/8 to any port 8080:8093/tcp  # Microservices

# Enable firewall
sudo ufw enable

# Check status
sudo ufw status verbose
```

#### Step 3: Install Java 21

```bash
# Add Adoptium repository
wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | sudo apt-key add -
echo "deb https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | sudo tee /etc/apt/sources.list.d/adoptium.list

# Update and install
sudo apt update
sudo apt install temurin-21-jdk -y

# Verify installation
java -version
# Expected: openjdk version "21.0.x"

# Set JAVA_HOME
echo 'export JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64' | sudo tee -a /etc/profile
echo 'export PATH=$JAVA_HOME/bin:$PATH' | sudo tee -a /etc/profile
source /etc/profile
```

---

## 4. DATABASE DEPLOYMENT

### 4.1 MySQL Installation & Configuration

#### Step 1: Install MySQL

```bash
# Install MySQL Server
sudo apt install mysql-server -y

# Secure installation
sudo mysql_secure_installation

# Responses:
# - Set root password: YES (use strong password)
# - Remove anonymous users: YES
# - Disallow root login remotely: YES
# - Remove test database: YES
# - Reload privilege tables: YES
```

#### Step 2: Configure MySQL for Production

```bash
# Edit MySQL configuration
sudo nano /etc/mysql/mysql.conf.d/mysqld.cnf

# Add/modify these settings:
```

```ini
[mysqld]
# Bind to all interfaces (or specific IP)
bind-address = 0.0.0.0

# Connection limits
max_connections = 500
max_allowed_packet = 256M

# Buffer pool (set to 70% of available RAM)
innodb_buffer_pool_size = 10G
innodb_buffer_pool_instances = 8

# Logging
log_error = /var/log/mysql/error.log
slow_query_log = 1
slow_query_log_file = /var/log/mysql/slow-query.log
long_query_time = 2

# Character set
character-set-server = utf8mb4
collation-server = utf8mb4_unicode_ci

# Binary logging (for replication)
server-id = 1
log_bin = /var/log/mysql/mysql-bin.log
binlog_format = ROW
expire_logs_days = 7

# InnoDB settings
innodb_flush_log_at_trx_commit = 2
innodb_log_file_size = 512M
innodb_file_per_table = 1

# Query cache (disabled in MySQL 8.0+)
# query_cache_size = 0
# query_cache_type = 0
```

```bash
# Restart MySQL
sudo systemctl restart mysql

# Enable MySQL on boot
sudo systemctl enable mysql
```

#### Step 3: Create Databases and Users

```bash
# Login to MySQL
sudo mysql -u root -p
```

```sql
-- Create production databases
CREATE DATABASE supasoft_auth CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_items CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_pricing CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_stock CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_sales CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_purchase CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_transfer CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_reports CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_notifications CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_integrations CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE supasoft_audit CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create application user (replace with strong password)
CREATE USER 'supasoft_app'@'%' IDENTIFIED BY 'STRONG_PASSWORD_HERE';

-- Grant privileges
GRANT ALL PRIVILEGES ON supasoft_auth.* TO 'supasoft_app'@'%';
GRANT ALL PRIVILEGES ON supasoft_items.* TO 'supasoft_app'@'%';
GRANT ALL PRIVILEGES ON supasoft_pricing.* TO 'supasoft_app'@'%';
GRANT ALL PRIVILEGES ON supasoft_stock.* TO 'supasoft_app'@'%';
GRANT ALL PRIVILEGES ON supasoft_sales.* TO 'supasoft_app'@'%';
GRANT ALL PRIVILEGES ON supasoft_purchase.* TO 'supasoft_app'@'%';
GRANT ALL PRIVILEGES ON supasoft_transfer.* TO 'supasoft_app'@'%';
GRANT ALL PRIVILEGES ON supasoft_reports.* TO 'supasoft_app'@'%';
GRANT ALL PRIVILEGES ON supasoft_notifications.* TO 'supasoft_app'@'%';
GRANT ALL PRIVILEGES ON supasoft_integrations.* TO 'supasoft_app'@'%';
GRANT ALL PRIVILEGES ON supasoft_audit.* TO 'supasoft_app'@'%';

-- Create read-only user for reports
CREATE USER 'supasoft_readonly'@'%' IDENTIFIED BY 'READONLY_PASSWORD_HERE';
GRANT SELECT ON supasoft_*.* TO 'supasoft_readonly'@'%';

-- Apply changes
FLUSH PRIVILEGES;

EXIT;
```

#### Step 4: Setup MySQL Replication (Optional)

**On Master (Primary) Server:**

```sql
-- Create replication user
CREATE USER 'replicator'@'%' IDENTIFIED BY 'REPLICATION_PASSWORD';
GRANT REPLICATION SLAVE ON *.* TO 'replicator'@'%';
FLUSH PRIVILEGES;

-- Show master status (note File and Position)
SHOW MASTER STATUS;
```

**On Replica (Secondary) Server:**

```bash
# Edit MySQL config
sudo nano /etc/mysql/mysql.conf.d/mysqld.cnf
```

```ini
[mysqld]
server-id = 2  # Different from master
read_only = 1
```

```bash
# Restart MySQL
sudo systemctl restart mysql
```

```sql
-- Configure replication
CHANGE MASTER TO
    MASTER_HOST='master_ip_address',
    MASTER_USER='replicator',
    MASTER_PASSWORD='REPLICATION_PASSWORD',
    MASTER_LOG_FILE='mysql-bin.000001',  -- From SHOW MASTER STATUS
    MASTER_LOG_POS=12345;  -- From SHOW MASTER STATUS

-- Start replication
START SLAVE;

-- Check replication status
SHOW SLAVE STATUS\G
```

### 4.2 Automated Backup Setup

```bash
# Create backup script
sudo nano /usr/local/bin/backup-mysql.sh
```

```bash
#!/bin/bash

# Configuration
BACKUP_DIR="/backup/mysql"
RETENTION_DAYS=30
MYSQL_USER="supasoft_app"
MYSQL_PASSWORD="STRONG_PASSWORD_HERE"
DATE=$(date +%Y%m%d_%H%M%S)

# Create backup directory
mkdir -p $BACKUP_DIR

# Databases to backup
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

# Backup each database
for DB in "${DATABASES[@]}"; do
    echo "Backing up $DB..."
    mysqldump -u $MYSQL_USER -p$MYSQL_PASSWORD \
        --single-transaction \
        --routines \
        --triggers \
        --events \
        $DB | gzip > "$BACKUP_DIR/${DB}_${DATE}.sql.gz"
done

# Delete old backups
find $BACKUP_DIR -name "*.sql.gz" -mtime +$RETENTION_DAYS -delete

echo "Backup completed: $DATE"
```

```bash
# Make script executable
sudo chmod +x /usr/local/bin/backup-mysql.sh

# Add to crontab (daily at 3 AM)
sudo crontab -e
```

```cron
0 3 * * * /usr/local/bin/backup-mysql.sh >> /var/log/mysql-backup.log 2>&1
```

---

## 5. REDIS DEPLOYMENT

### 5.1 Install and Configure Redis

```bash
# Install Redis
sudo apt install redis-server -y

# Edit Redis configuration
sudo nano /etc/redis/redis.conf
```

```conf
# Bind to specific IP (change to your server IP)
bind 0.0.0.0

# Set password (IMPORTANT!)
requirepass YOUR_STRONG_REDIS_PASSWORD

# Maximum memory
maxmemory 8gb
maxmemory-policy allkeys-lru

# Persistence
save 900 1
save 300 10
save 60 10000

# Append-only file
appendonly yes
appendfsync everysec

# Log level
loglevel notice
logfile /var/log/redis/redis-server.log
```

```bash
# Restart Redis
sudo systemctl restart redis-server

# Enable on boot
sudo systemctl enable redis-server

# Test connection
redis-cli -a YOUR_STRONG_REDIS_PASSWORD ping
# Expected: PONG
```

### 5.2 Redis Cluster (Optional - for HA)

For high availability, deploy Redis Sentinel or Redis Cluster. See Redis documentation for setup.

---

## 6. RABBITMQ DEPLOYMENT

### 6.1 Install RabbitMQ

```bash
# Add RabbitMQ repository
curl -s https://packagecloud.io/install/repositories/rabbitmq/rabbitmq-server/script.deb.sh | sudo bash

# Install RabbitMQ
sudo apt install rabbitmq-server -y

# Start RabbitMQ
sudo systemctl start rabbitmq-server

# Enable on boot
sudo systemctl enable rabbitmq-server

# Enable management plugin
sudo rabbitmq-plugins enable rabbitmq_management

# Create admin user
sudo rabbitmqctl add_user admin STRONG_ADMIN_PASSWORD
sudo rabbitmqctl set_user_tags admin administrator
sudo rabbitmqctl set_permissions -p / admin ".*" ".*" ".*"

# Delete default guest user (security)
sudo rabbitmqctl delete_user guest
```

### 6.2 Access RabbitMQ Management UI

- **URL**: https://your-server-ip:15672
- **Username**: admin
- **Password**: STRONG_ADMIN_PASSWORD

---

## 7. APPLICATION DEPLOYMENT

### 7.1 Create Application User

```bash
# Create dedicated user
sudo useradd -m -s /bin/bash supasoft

# Create application directories
sudo mkdir -p /opt/supasoft/{logs,config,jars}

# Set ownership
sudo chown -R supasoft:supasoft /opt/supasoft
```

### 7.2 Upload JAR Files

```bash
# From your local machine, build JARs
mvn clean package -DskipTests

# Upload to server (example for item-service)
scp item-service/target/item-service-1.0.0.jar supasoft@server-ip:/opt/supasoft/jars/

# Repeat for all services
```

### 7.3 Create Configuration Files

```bash
# Create production config
sudo nano /opt/supasoft/config/application-prod.properties
```

```properties
# Database Configuration (Item Service example)
spring.datasource.url=jdbc:mysql://db-server-ip:3306/supasoft_items?useSSL=true&serverTimezone=Africa/Nairobi
spring.datasource.username=supasoft_app
spring.datasource.password=STRONG_PASSWORD_HERE

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# Redis Configuration
spring.data.redis.host=redis-server-ip
spring.data.redis.port=6379
spring.data.redis.password=YOUR_STRONG_REDIS_PASSWORD

# RabbitMQ Configuration
spring.rabbitmq.host=rabbitmq-server-ip
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=STRONG_ADMIN_PASSWORD

# Eureka Configuration
eureka.client.service-url.defaultZone=http://discovery-server-ip:8761/eureka

# Logging
logging.level.root=WARN
logging.level.com.supasoft=INFO
logging.file.name=/opt/supasoft/logs/item-service.log
logging.file.max-size=100MB
logging.file.max-history=30

# Actuator (restrict in production)
management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.endpoint.health.show-details=when-authorized
```

### 7.4 Create Systemd Services

**Example: Item Service**

```bash
sudo nano /etc/systemd/system/supasoft-item.service
```

```ini
[Unit]
Description=Supasoft Item Service
After=syslog.target network.target

[Service]
User=supasoft
Group=supasoft
Type=simple
WorkingDirectory=/opt/supasoft
ExecStart=/usr/bin/java \
    -Xms512m \
    -Xmx2g \
    -XX:+UseG1GC \
    -XX:MaxGCPauseMillis=200 \
    -Dspring.profiles.active=prod \
    -Dspring.config.location=/opt/supasoft/config/application-prod.properties \
    -jar /opt/supasoft/jars/item-service-1.0.0.jar
Restart=on-failure
RestartSec=10
StandardOutput=journal
StandardError=journal
SyslogIdentifier=supasoft-item

[Install]
WantedBy=multi-user.target
```

```bash
# Reload systemd
sudo systemctl daemon-reload

# Enable service
sudo systemctl enable supasoft-item.service

# Start service
sudo systemctl start supasoft-item.service

# Check status
sudo systemctl status supasoft-item.service

# View logs
sudo journalctl -u supasoft-item.service -f
```

**Repeat for all services:**
- supasoft-discovery.service (Port 8761)
- supasoft-gateway.service (Port 8080)
- supasoft-auth.service (Port 8090)
- supasoft-pricing.service (Port 8082)
- supasoft-stock.service (Port 8083)
- supasoft-sales.service (Port 8084)
- supasoft-purchase.service (Port 8085)
- supasoft-transfer.service (Port 8086)
- supasoft-reports.service (Port 8087)
- supasoft-notification.service (Port 8091)
- supasoft-integration.service (Port 8092)
- supasoft-audit.service (Port 8093)

---

## 8. LOAD BALANCER SETUP (NGINX)

### 8.1 Install Nginx

```bash
# Install Nginx
sudo apt install nginx -y

# Start Nginx
sudo systemctl start nginx

# Enable on boot
sudo systemctl enable nginx
```

### 8.2 Configure Nginx

```bash
# Create Supasoft configuration
sudo nano /etc/nginx/sites-available/supasoft
```

```nginx
# Upstream servers (API Gateway instances)
upstream api_gateway {
    least_conn;
    server app-server-1:8080 max_fails=3 fail_timeout=30s;
    server app-server-2:8080 max_fails=3 fail_timeout=30s;
    server app-server-3:8080 max_fails=3 fail_timeout=30s;
}

# Rate limiting
limit_req_zone $binary_remote_addr zone=api_limit:10m rate=100r/m;
limit_conn_zone $binary_remote_addr zone=conn_limit:10m;

# HTTP to HTTPS redirect
server {
    listen 80;
    server_name api.supasoft.com;
    return 301 https://$server_name$request_uri;
}

# HTTPS server
server {
    listen 443 ssl http2;
    server_name api.supasoft.com;

    # SSL Configuration
    ssl_certificate /etc/letsencrypt/live/api.supasoft.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/api.supasoft.com/privkey.pem;
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;
    ssl_prefer_server_ciphers on;
    ssl_session_cache shared:SSL:10m;
    ssl_session_timeout 10m;

    # Security headers
    add_header X-Frame-Options "SAMEORIGIN" always;
    add_header X-Content-Type-Options "nosniff" always;
    add_header X-XSS-Protection "1; mode=block" always;
    add_header Strict-Transport-Security "max-age=31536000; includeSubDomains" always;

    # Logging
    access_log /var/log/nginx/supasoft_access.log;
    error_log /var/log/nginx/supasoft_error.log;

    # Client settings
    client_max_body_size 10M;
    client_body_timeout 30s;
    client_header_timeout 30s;

    # Proxy settings
    proxy_connect_timeout 30s;
    proxy_send_timeout 30s;
    proxy_read_timeout 30s;
    proxy_buffering off;

    # API endpoints
    location /api/ {
        # Rate limiting
        limit_req zone=api_limit burst=20 nodelay;
        limit_conn conn_limit 10;

        # Proxy to API Gateway
        proxy_pass http://api_gateway;
        proxy_http_version 1.1;
        
        # Headers
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header Connection "";
        
        # CORS (if needed)
        add_header 'Access-Control-Allow-Origin' '*' always;
        add_header 'Access-Control-Allow-Methods' 'GET, POST, PUT, DELETE, OPTIONS' always;
        add_header 'Access-Control-Allow-Headers' 'Authorization, Content-Type' always;
        
        if ($request_method = 'OPTIONS') {
            return 204;
        }
    }

    # Health check endpoint
    location /health {
        access_log off;
        return 200 "healthy\n";
        add_header Content-Type text/plain;
    }

    # Deny access to sensitive files
    location ~ /\. {
        deny all;
        access_log off;
        log_not_found off;
    }
}
```

```bash
# Enable site
sudo ln -s /etc/nginx/sites-available/supasoft /etc/nginx/sites-enabled/

# Remove default site
sudo rm /etc/nginx/sites-enabled/default

# Test configuration
sudo nginx -t

# Reload Nginx
sudo systemctl reload nginx
```

### 8.3 Setup SSL with Let's Encrypt

```bash
# Install Certbot
sudo apt install certbot python3-certbot-nginx -y

# Obtain certificate
sudo certbot --nginx -d api.supasoft.com

# Auto-renewal (already configured by certbot)
# Test renewal
sudo certbot renew --dry-run
```

---

## 9. MONITORING SETUP

### 9.1 Install Prometheus

```bash
# Create Prometheus user
sudo useradd --no-create-home --shell /bin/false prometheus

# Create directories
sudo mkdir /etc/prometheus /var/lib/prometheus

# Download Prometheus
cd /tmp
wget https://github.com/prometheus/prometheus/releases/download/v2.45.0/prometheus-2.45.0.linux-amd64.tar.gz
tar -xvf prometheus-2.45.0.linux-amd64.tar.gz
cd prometheus-2.45.0.linux-amd64

# Copy binaries
sudo cp prometheus /usr/local/bin/
sudo cp promtool /usr/local/bin/

# Copy config
sudo cp -r consoles /etc/prometheus
sudo cp -r console_libraries /etc/prometheus
sudo cp prometheus.yml /etc/prometheus/

# Set ownership
sudo chown -R prometheus:prometheus /etc/prometheus /var/lib/prometheus
sudo chown prometheus:prometheus /usr/local/bin/prometheus /usr/local/bin/promtool

# Configure Prometheus
sudo nano /etc/prometheus/prometheus.yml
```

```yaml
global:
  scrape_interval: 15s
  evaluation_interval: 15s

scrape_configs:
  - job_name: 'spring-boot-apps'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets:
        - 'app-server-1:8081'  # Item Service
        - 'app-server-1:8082'  # Pricing Service
        - 'app-server-1:8083'  # Stock Service
        - 'app-server-1:8084'  # Sales Service
        # Add all services...
```

```bash
# Create systemd service
sudo nano /etc/systemd/system/prometheus.service
```

```ini
[Unit]
Description=Prometheus
Wants=network-online.target
After=network-online.target

[Service]
User=prometheus
Group=prometheus
Type=simple
ExecStart=/usr/local/bin/prometheus \
    --config.file /etc/prometheus/prometheus.yml \
    --storage.tsdb.path /var/lib/prometheus/ \
    --web.console.templates=/etc/prometheus/consoles \
    --web.console.libraries=/etc/prometheus/console_libraries

[Install]
WantedBy=multi-user.target
```

```bash
# Start Prometheus
sudo systemctl daemon-reload
sudo systemctl start prometheus
sudo systemctl enable prometheus

# Access Prometheus UI: http://server-ip:9090
```

### 9.2 Install Grafana

```bash
# Add Grafana repository
sudo apt-get install -y software-properties-common
sudo add-apt-repository "deb https://packages.grafana.com/oss/deb stable main"
wget -q -O - https://packages.grafana.com/gpg.key | sudo apt-key add -

# Install Grafana
sudo apt update
sudo apt install grafana -y

# Start Grafana
sudo systemctl start grafana-server
sudo systemctl enable grafana-server

# Access Grafana UI: http://server-ip:3000
# Default credentials: admin / admin
```

**Configure Grafana:**
1. Add Prometheus as data source
2. Import Spring Boot dashboards
3. Create custom dashboards for business metrics

---

## 10. POST-DEPLOYMENT VERIFICATION

### 10.1 Service Health Checks

```bash
# Check all services
sudo systemctl status supasoft-*

# Check Eureka dashboard
curl http://app-server-1:8761/eureka/apps

# Check API Gateway health
curl https://api.supasoft.com/api/v1/actuator/health
```

### 10.2 API Testing

```bash
# Test authentication
curl -X POST https://api.supasoft.com/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"Admin@123"}'

# Test item service
curl -X GET https://api.supasoft.com/api/v1/items \
  -H "Authorization: Bearer <token>"
```

### 10.3 Performance Testing

```bash
# Install Apache Bench
sudo apt install apache2-utils -y

# Load test (100 concurrent, 1000 requests)
ab -n 1000 -c 100 -H "Authorization: Bearer <token>" \
  https://api.supasoft.com/api/v1/items
```

---

## 11. BACKUP & DISASTER RECOVERY

### 11.1 Database Backup

Already configured in Section 4.2. Verify:

```bash
# Check backup files
ls -lh /backup/mysql/

# Test restoration
mysql -u supasoft_app -p supasoft_items < /backup/mysql/supasoft_items_20251113_030000.sql.gz
```

### 11.2 Application Backup

```bash
# Backup JAR files and configs
sudo tar -czf /backup/supasoft-jars-$(date +%Y%m%d).tar.gz /opt/supasoft/jars
sudo tar -czf /backup/supasoft-config-$(date +%Y%m%d).tar.gz /opt/supasoft/config

# Backup Nginx configs
sudo tar -czf /backup/nginx-config-$(date +%Y%m%d).tar.gz /etc/nginx
```

### 11.3 Disaster Recovery Plan

1. **Database Failure**: Switch to replica, promote to master
2. **Application Server Failure**: Load balancer routes to healthy servers
3. **Complete Data Center Failure**: Restore from off-site backups

---

## 12. MAINTENANCE PROCEDURES

### 12.1 Rolling Updates

```bash
# Update one server at a time
# 1. Remove server from load balancer
sudo nano /etc/nginx/sites-available/supasoft
# Comment out server line, reload nginx
sudo systemctl reload nginx

# 2. Stop service
sudo systemctl stop supasoft-item.service

# 3. Replace JAR file
sudo cp item-service-1.1.0.jar /opt/supasoft/jars/item-service-1.0.0.jar

# 4. Start service
sudo systemctl start supasoft-item.service

# 5. Verify health
curl http://localhost:8081/actuator/health

# 6. Add server back to load balancer
# Uncomment server line, reload nginx
sudo systemctl reload nginx

# 7. Repeat for other servers
```

### 12.2 Log Rotation

```bash
# Configure logrotate
sudo nano /etc/logrotate.d/supasoft
```

```
/opt/supasoft/logs/*.log {
    daily
    rotate 30
    compress
    delaycompress
    missingok
    notifempty
    create 0644 supasoft supasoft
    sharedscripts
    postrotate
        systemctl reload supasoft-* > /dev/null 2>&1 || true
    endscript
}
```

---

## 13. SECURITY HARDENING

### 13.1 Fail2Ban

```bash
# Install Fail2Ban
sudo apt install fail2ban -y

# Configure
sudo cp /etc/fail2ban/jail.conf /etc/fail2ban/jail.local
sudo nano /etc/fail2ban/jail.local

# Add custom jail for Supasoft
sudo nano /etc/fail2ban/filter.d/supasoft.conf
```

```ini
[Definition]
failregex = ^.*Authentication failed for user.*from <HOST>.*$
ignoreregex =
```

```bash
# Enable and start
sudo systemctl enable fail2ban
sudo systemctl start fail2ban
```

### 13.2 Regular Security Updates

```bash
# Enable automatic security updates
sudo apt install unattended-upgrades -y
sudo dpkg-reconfigure --priority=low unattended-upgrades
```

---

## 14. TROUBLESHOOTING

### 14.1 Service Won't Start

```bash
# Check logs
sudo journalctl -u supasoft-item.service -n 100

# Check Java process
ps aux | grep java

# Check port availability
sudo netstat -tulpn | grep 8081
```

### 14.2 Database Connection Issues

```bash
# Test MySQL connection
mysql -h db-server-ip -u supasoft_app -p

# Check MySQL logs
sudo tail -f /var/log/mysql/error.log

# Check firewall
sudo ufw status
```

### 14.3 Performance Issues

```bash
# Check CPU and memory
htop

# Check disk I/O
iostat -x 1

# Check network
iftop

# Check service logs for slow queries
grep -i "slow" /opt/supasoft/logs/*.log
```

---

## 15. DEPLOYMENT CHECKLIST

### Pre-Deployment
- [ ] All servers provisioned and updated
- [ ] Firewall configured
- [ ] SSL certificates obtained
- [ ] Database users created
- [ ] Environment variables set
- [ ] JAR files built and uploaded
- [ ] systemd services created

### Deployment
- [ ] MySQL running and replicated
- [ ] Redis running
- [ ] RabbitMQ running
- [ ] All microservices started
- [ ] Services registered with Eureka
- [ ] Load balancer configured
- [ ] SSL working

### Post-Deployment
- [ ] Health checks passing
- [ ] API endpoints responding
- [ ] Authentication working
- [ ] Database migrations applied
- [ ] Backups configured
- [ ] Monitoring setup
- [ ] Logs accessible
- [ ] Performance tested
- [ ] Documentation updated
- [ ] Team trained

---

**Document Version**: 1.0  
**Last Updated**: November 2025  
**Target Environment**: Production  
**Author**: Supasoft DevOps Team

