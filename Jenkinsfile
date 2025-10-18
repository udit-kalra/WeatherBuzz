pipeline {
    agent any

    tools {
            jdk ‘jdk17’  // This must match the JDK name you configured in Jenkins Global Tool Configuration
        }

    environment {
        ANDROID_HOME = "/Users/udit/Library/Android/sdk"
        PATH = "$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools"
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/udit-kalra/WeatherBuzz.git'
            }
        }
        stage('Prepare') {
            steps {
                // Fix permission issue on macOS
                sh 'chmod +x gradlew'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean assembleDebug'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
    }
    post {
        success {
            echo '✅ Build Successful!'
        }
        failure {
            echo '❌ Build Failed!'
        }
    }
}
