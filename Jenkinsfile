pipeline {
    agent any
    tools {
        gradle 'gradle 6.7'
    }
    stages {
        stage('Gradle') {
            steps {
                sh 'gradle --version'
                sh 'docker --version'
                sh 'docker-compose --version'
            }
        }
        stage('Build') {
            steps {
                sh 'gradle assemble'
            }
        }
        stage('Test') {
            steps {
                sh 'gradle test'
            }
        }
        stage('Deploy') {
            steps {
                sh "docker-compose up --build -d"
            }
        }
    }
}