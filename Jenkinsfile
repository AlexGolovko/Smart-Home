pipeline {
    agent any

    triggers {
        pollSCM '* * * * *'
    }
    stages {
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
        stage('Run docker container'){
            steps{
                sh './smarthome/gradlew assemble dockerRun'}
            }
    }
}