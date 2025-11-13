// ============================================================================
// Supasoft Jenkins Pipeline
// ============================================================================
// Description: Declarative Jenkins pipeline for CI/CD automation
// Requirements: Jenkins with Docker, Maven, and necessary plugins
// ============================================================================

pipeline {
    agent any
    
    // Environment variables
    environment {
        // Maven settings
        MAVEN_HOME = tool 'Maven-3.9.5'
        MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository'
        
        // Java settings
        JAVA_HOME = tool 'JDK-21'
        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${PATH}"
        
        // Application settings
        APP_NAME = 'supasoft-api'
        REGISTRY_URL = 'docker.io'
        DOCKER_REGISTRY = credentials('docker-registry-credentials')
        
        // Notification settings
        SLACK_CHANNEL = '#supasoft-builds'
        EMAIL_RECIPIENTS = 'dev-team@supasoft.com'
        
        // SonarQube
        SONAR_TOKEN = credentials('sonarqube-token')
        
        // Version
        VERSION = "${env.BUILD_NUMBER}"
        GIT_COMMIT_SHORT = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
    }
    
    // Build triggers
    triggers {
        // Poll SCM every 15 minutes
        pollSCM('H/15 * * * *')
        
        // Scheduled nightly build
        cron('H 2 * * *')
    }
    
    // Pipeline options
    options {
        // Keep last 10 builds
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
        
        // Timeout after 1 hour
        timeout(time: 1, unit: 'HOURS')
        
        // Timestamps in console output
        timestamps()
        
        // Disable concurrent builds
        disableConcurrentBuilds()
        
        // Skip default checkout
        skipDefaultCheckout(false)
    }
    
    // Build parameters
    parameters {
        choice(
            name: 'BUILD_TYPE',
            choices: ['snapshot', 'release'],
            description: 'Type of build to perform'
        )
        booleanParam(
            name: 'RUN_SECURITY_SCAN',
            defaultValue: false,
            description: 'Run security vulnerability scan'
        )
        booleanParam(
            name: 'DEPLOY_TO_DEV',
            defaultValue: false,
            description: 'Deploy to development environment after build'
        )
        string(
            name: 'DEPLOY_VERSION',
            defaultValue: 'latest',
            description: 'Version to deploy (for deployment jobs)'
        )
    }
    
    // ========================================================================
    // Pipeline Stages
    // ========================================================================
    
    stages {
        
        // ====================================================================
        // Stage: Checkout
        // ====================================================================
        stage('Checkout') {
            steps {
                script {
                    echo "========================================="
                    echo "Checking out source code..."
                    echo "========================================="
                    
                    checkout scm
                    
                    // Display Git information
                    sh '''
                        echo "Git Branch: ${GIT_BRANCH}"
                        echo "Git Commit: ${GIT_COMMIT}"
                        echo "Git Commit Short: ${GIT_COMMIT_SHORT}"
                    '''
                }
            }
        }
        
        // ====================================================================
        // Stage: Environment Setup
        // ====================================================================
        stage('Environment Setup') {
            steps {
                script {
                    echo "========================================="
                    echo "Setting up build environment..."
                    echo "========================================="
                    
                    sh '''
                        echo "Java Version:"
                        java -version
                        
                        echo "Maven Version:"
                        mvn --version
                        
                        echo "Docker Version:"
                        docker --version
                    '''
                }
            }
        }
        
        // ====================================================================
        // Stage: Validate
        // ====================================================================
        stage('Validate') {
            steps {
                script {
                    echo "========================================="
                    echo "Validating project structure..."
                    echo "========================================="
                    
                    sh 'mvn validate'
                }
            }
        }
        
        // ====================================================================
        // Stage: Compile
        // ====================================================================
        stage('Compile') {
            steps {
                script {
                    echo "========================================="
                    echo "Compiling project..."
                    echo "========================================="
                    
                    sh 'mvn clean compile -DskipTests'
                }
            }
        }
        
        // ====================================================================
        // Stage: Unit Tests
        // ====================================================================
        stage('Unit Tests') {
            steps {
                script {
                    echo "========================================="
                    echo "Running unit tests..."
                    echo "========================================="
                    
                    sh 'mvn test'
                }
            }
            post {
                always {
                    // Publish test results
                    junit '**/target/surefire-reports/*.xml'
                    
                    // Publish code coverage
                    jacoco(
                        execPattern: '**/target/jacoco.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java'
                    )
                }
            }
        }
        
        // ====================================================================
        // Stage: Integration Tests
        // ====================================================================
        stage('Integration Tests') {
            when {
                anyOf {
                    branch 'main'
                    branch 'develop'
                    changeRequest()
                }
            }
            steps {
                script {
                    echo "========================================="
                    echo "Running integration tests..."
                    echo "========================================="
                    
                    // Start test infrastructure
                    sh '''
                        docker-compose -f docker-compose-test.yml up -d mysql redis rabbitmq
                        sleep 30
                    '''
                    
                    // Run integration tests
                    sh 'mvn verify -DskipUnitTests=true'
                }
            }
            post {
                always {
                    // Stop test infrastructure
                    sh 'docker-compose -f docker-compose-test.yml down || true'
                    
                    // Publish integration test results
                    junit '**/target/failsafe-reports/*.xml'
                }
            }
        }
        
        // ====================================================================
        // Stage: Code Quality Analysis
        // ====================================================================
        stage('Code Quality') {
            parallel {
                stage('SonarQube Analysis') {
                    when {
                        anyOf {
                            branch 'main'
                            branch 'develop'
                        }
                    }
                    steps {
                        script {
                            echo "Running SonarQube analysis..."
                            
                            withSonarQubeEnv('SonarQube-Server') {
                                sh '''
                                    mvn sonar:sonar \
                                        -Dsonar.projectKey=supasoft-api \
                                        -Dsonar.projectName="Supasoft API" \
                                        -Dsonar.branch.name=${GIT_BRANCH}
                                '''
                            }
                        }
                    }
                }
                
                stage('Checkstyle') {
                    steps {
                        script {
                            echo "Running Checkstyle..."
                            sh 'mvn checkstyle:checkstyle'
                        }
                    }
                    post {
                        always {
                            recordIssues(
                                tools: [checkStyle()],
                                qualityGates: [[threshold: 10, type: 'TOTAL', unstable: true]]
                            )
                        }
                    }
                }
                
                stage('PMD') {
                    steps {
                        script {
                            echo "Running PMD..."
                            sh 'mvn pmd:pmd'
                        }
                    }
                    post {
                        always {
                            recordIssues(tools: [pmdParser(pattern: '**/target/pmd.xml')])
                        }
                    }
                }
            }
        }
        
        // ====================================================================
        // Stage: Security Scan
        // ====================================================================
        stage('Security Scan') {
            when {
                expression { params.RUN_SECURITY_SCAN == true }
            }
            steps {
                script {
                    echo "========================================="
                    echo "Running security vulnerability scan..."
                    echo "========================================="
                    
                    sh 'mvn dependency-check:check'
                }
            }
            post {
                always {
                    publishHTML([
                        reportDir: 'target',
                        reportFiles: 'dependency-check-report.html',
                        reportName: 'OWASP Dependency Check Report'
                    ])
                }
            }
        }
        
        // ====================================================================
        // Stage: Package
        // ====================================================================
        stage('Package') {
            when {
                anyOf {
                    branch 'main'
                    branch 'develop'
                    tag pattern: 'v\\d+\\.\\d+\\.\\d+', comparator: 'REGEXP'
                }
            }
            steps {
                script {
                    echo "========================================="
                    echo "Packaging application..."
                    echo "========================================="
                    
                    sh 'mvn package -DskipTests'
                }
            }
            post {
                success {
                    archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                }
            }
        }
        
        // ====================================================================
        // Stage: Build Docker Images
        // ====================================================================
        stage('Build Docker Images') {
            when {
                anyOf {
                    branch 'main'
                    branch 'develop'
                    tag pattern: 'v\\d+\\.\\d+\\.\\d+', comparator: 'REGEXP'
                }
            }
            steps {
                script {
                    echo "========================================="
                    echo "Building Docker images..."
                    echo "========================================="
                    
                    // List of services to build
                    def services = [
                        'discovery',
                        'api-gateway',
                        'auth-service',
                        'item-service',
                        'pricing-service',
                        'stock-service',
                        'sales-service',
                        'purchase-service',
                        'transfer-service',
                        'reports-service',
                        'notification-service',
                        'integration-service'
                    ]
                    
                    // Build images
                    services.each { service ->
                        echo "Building ${service}..."
                        
                        def imageName = "${env.REGISTRY_URL}/supasoft/${service}:${env.VERSION}"
                        def latestImage = "${env.REGISTRY_URL}/supasoft/${service}:latest"
                        
                        sh """
                            docker build -t ${imageName} -f ${service}/Dockerfile .
                            docker tag ${imageName} ${latestImage}
                        """
                    }
                }
            }
        }
        
        // ====================================================================
        // Stage: Push Docker Images
        // ====================================================================
        stage('Push Docker Images') {
            when {
                branch 'main'
            }
            steps {
                script {
                    echo "========================================="
                    echo "Pushing Docker images to registry..."
                    echo "========================================="
                    
                    docker.withRegistry("https://${env.REGISTRY_URL}", 'docker-registry-credentials') {
                        def services = [
                            'discovery', 'api-gateway', 'auth-service', 'item-service',
                            'pricing-service', 'stock-service', 'sales-service',
                            'purchase-service', 'transfer-service', 'reports-service',
                            'notification-service', 'integration-service'
                        ]
                        
                        services.each { service ->
                            def imageName = "${env.REGISTRY_URL}/supasoft/${service}"
                            sh """
                                docker push ${imageName}:${env.VERSION}
                                docker push ${imageName}:latest
                            """
                        }
                    }
                }
            }
        }
        
        // ====================================================================
        // Stage: Deploy to Development
        // ====================================================================
        stage('Deploy to Development') {
            when {
                allOf {
                    branch 'develop'
                    expression { params.DEPLOY_TO_DEV == true }
                }
            }
            steps {
                script {
                    echo "========================================="
                    echo "Deploying to Development environment..."
                    echo "========================================="
                    
                    // SSH to dev server and deploy
                    sshagent(['dev-server-ssh-key']) {
                        sh '''
                            ssh -o StrictHostKeyChecking=no user@dev-server.supasoft.com '
                                cd /opt/supasoft &&
                                docker-compose pull &&
                                docker-compose up -d &&
                                docker-compose ps
                            '
                        '''
                    }
                }
            }
        }
        
        // ====================================================================
        // Stage: Deploy to Staging
        // ====================================================================
        stage('Deploy to Staging') {
            when {
                branch 'main'
            }
            steps {
                script {
                    echo "========================================="
                    echo "Deploying to Staging environment..."
                    echo "========================================="
                    
                    input message: 'Deploy to Staging?', ok: 'Deploy'
                    
                    sshagent(['staging-server-ssh-key']) {
                        sh '''
                            ssh -o StrictHostKeyChecking=no user@staging-server.supasoft.com '
                                cd /opt/supasoft &&
                                docker-compose pull &&
                                docker-compose up -d &&
                                docker-compose ps
                            '
                        '''
                    }
                }
            }
        }
        
        // ====================================================================
        // Stage: Deploy to Production
        // ====================================================================
        stage('Deploy to Production') {
            when {
                tag pattern: 'v\\d+\\.\\d+\\.\\d+', comparator: 'REGEXP'
            }
            steps {
                script {
                    echo "========================================="
                    echo "Deploying to Production environment..."
                    echo "========================================="
                    
                    input message: 'Deploy to Production?', ok: 'Deploy', submitter: 'admin,ops-team'
                    
                    sshagent(['prod-server-ssh-key']) {
                        sh '''
                            ssh -o StrictHostKeyChecking=no user@prod-server.supasoft.com '
                                cd /opt/supasoft &&
                                docker-compose pull &&
                                docker-compose up -d --no-deps --build &&
                                docker-compose ps
                            '
                        '''
                    }
                }
            }
        }
        
        // ====================================================================
        // Stage: Smoke Tests
        // ====================================================================
        stage('Smoke Tests') {
            when {
                anyOf {
                    branch 'main'
                    tag pattern: 'v\\d+\\.\\d+\\.\\d+', comparator: 'REGEXP'
                }
            }
            steps {
                script {
                    echo "========================================="
                    echo "Running smoke tests..."
                    echo "========================================="
                    
                    sleep 30 // Wait for services to be ready
                    
                    sh '''
                        # Test API Gateway health
                        curl -f http://api-server/actuator/health || exit 1
                        
                        # Test authentication
                        curl -f http://api-server/api/v1/auth/health || exit 1
                        
                        echo "Smoke tests passed!"
                    '''
                }
            }
        }
    }
    
    // ========================================================================
    // Post Actions
    // ========================================================================
    
    post {
        always {
            echo "Pipeline completed."
            
            // Clean up workspace
            cleanWs()
        }
        
        success {
            echo "Build succeeded!"
            
            // Send success notification
            slackSend(
                channel: env.SLACK_CHANNEL,
                color: 'good',
                message: """
                    ✅ *Build Successful*
                    Job: ${env.JOB_NAME}
                    Build: ${env.BUILD_NUMBER}
                    Branch: ${env.GIT_BRANCH}
                    Commit: ${env.GIT_COMMIT_SHORT}
                    Duration: ${currentBuild.durationString}
                    """
            )
            
            emailext(
                subject: "✅ Build Success: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: """
                    Build succeeded!
                    
                    Job: ${env.JOB_NAME}
                    Build Number: ${env.BUILD_NUMBER}
                    Branch: ${env.GIT_BRANCH}
                    Commit: ${env.GIT_COMMIT_SHORT}
                    
                    Check console output at: ${env.BUILD_URL}
                    """,
                to: env.EMAIL_RECIPIENTS
            )
        }
        
        failure {
            echo "Build failed!"
            
            // Send failure notification
            slackSend(
                channel: env.SLACK_CHANNEL,
                color: 'danger',
                message: """
                    ❌ *Build Failed*
                    Job: ${env.JOB_NAME}
                    Build: ${env.BUILD_NUMBER}
                    Branch: ${env.GIT_BRANCH}
                    Commit: ${env.GIT_COMMIT_SHORT}
                    Duration: ${currentBuild.durationString}
                    Console: ${env.BUILD_URL}console
                    """
            )
            
            emailext(
                subject: "❌ Build Failure: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: """
                    Build failed!
                    
                    Job: ${env.JOB_NAME}
                    Build Number: ${env.BUILD_NUMBER}
                    Branch: ${env.GIT_BRANCH}
                    Commit: ${env.GIT_COMMIT_SHORT}
                    
                    Check console output at: ${env.BUILD_URL}console
                    """,
                to: env.EMAIL_RECIPIENTS,
                attachLog: true
            )
        }
        
        unstable {
            echo "Build is unstable!"
            
            slackSend(
                channel: env.SLACK_CHANNEL,
                color: 'warning',
                message: """
                    ⚠️ *Build Unstable*
                    Job: ${env.JOB_NAME}
                    Build: ${env.BUILD_NUMBER}
                    Branch: ${env.GIT_BRANCH}
                    """
            )
        }
    }
}

