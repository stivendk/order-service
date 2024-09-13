pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/stivendk/order-service.git'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t my-docker-image:latest .'
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker run -d -p 8082:8082 my-docker-image:latest'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
