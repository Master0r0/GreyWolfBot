pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh './gradlew clean'
        sh './gradlew.assemble'
      }
    }
  }
}