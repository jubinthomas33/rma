pipeline {
    agent any

    tools {
        jdk 'JDK21'      // Name of Java 21 installation in Jenkins
        maven 'Maven3'   // Name of Maven installation in Jenkins
    }

   

    stages {

        stage('Checkout') {
            steps {
                // Checkout code from Git
                git url: 'https://github.com/jubinthomas33/rma.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                // Run Maven build on Windows
                bat 'mvn clean install'
            }
        }

        stage('Archive Artifacts') {
            steps {
                // Archive all JAR files from target folder
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Run Tests') {
            steps {
                // Publish JUnit test results
                junit 'target/surefire-reports/*.xml'
            }
        }

    }

    post {
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed. Check console output for details.'
        }
    }
}
