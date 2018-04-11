pipeline {
agent any;
  
tools{
  maven 'maven 3'
  jdk 'java 8'
}
  
stages {
  stage ("initialize") {
    steps {
      sh '''
         echo "PATH = ${PATH}"
         echo "M2_HOME = ${M2_HOME}"
       '''
    }
}

stage ('Build project') {
  steps {
    sh 'mvn clean verify
        }
  }
}
