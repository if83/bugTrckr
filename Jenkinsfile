pipeline {
agent any;
  
tools{
  maven 'maven 3.5.3'
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
}
  
stage ('Build project') {
  steps {
    sh 'mvn clean package'
   }
}
}
