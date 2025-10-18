pipeline {
    agent any
    environment {
        JAVA_HOME = "/usr/local/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home"
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
