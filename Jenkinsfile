pipeline {
    agent any
    tools {
        maven 'maven 3.5.3'
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage('Checkout') {
            steps {
                git branch: 'dev', url: 'https://github.com/if83/bugTrckr.git'
            }
        }
        stage ('Build') {
            steps {
                sh 'mvn -B -V -U -e clean package' 
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml' 
                }
            }
        }
    }
}
