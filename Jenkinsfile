pipeline {
    agent any
    tools {
            gradle
        }
    stages {
        stage('Gradle') {
            steps {
                sh 'gradle --version'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew assemble'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker-compose up'
            }
        }
    }
}