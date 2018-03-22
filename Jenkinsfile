pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh './gradlew clean'
        sh './gradlew assemble'
      }
    }
    stage('Create') {
      parallel {
        stage('Archive Artifacts') {
          steps {
            archiveArtifacts(artifacts: 'build/libs/**', allowEmptyArchive: true)
          }
        }
        stage('Create Changelog') {
          steps {
            junit(testResults: 'build/test-results/**', allowEmptyResults: true)
          }
        }
      }
    }
  }
}