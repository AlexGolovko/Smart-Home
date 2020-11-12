pipeline {
    agent any
    tools {
        gradle 'gradle 6.7'
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