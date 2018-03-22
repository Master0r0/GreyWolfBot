pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh './gradlew clean'
        sh './gradlew assemble'
      }
    }
    stage('Adjust Files') {
      steps {
        archiveArtifacts(artifacts: './libs/**', allowEmptyArchive: true)
      }
    }
  }
}