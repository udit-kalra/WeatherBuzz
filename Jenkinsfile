pipeline {
    agent any

    tools {
        jdk 'jdk17'  // Must match the JDK name configured in Jenkins Global Tool Configuration
    }

    environment {
        ANDROID_HOME = "/Users/ukalra/Library/Android/sdk"
        PATH = "$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools"
        DESKTOP_BUILD_DIR = "/Users/ukalra/Desktop/AndroidBuilds"
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

                // Generate local.properties for Android SDK
                sh """
                    echo "sdk.dir=${ANDROID_HOME}" > local.properties
                    cat local.properties
                """

                // Create Desktop folder for builds
                sh "mkdir -p ${DESKTOP_BUILD_DIR}"
            }
        }

        stage('Build Debug APK') {
            steps {
                sh './gradlew assembleDebug'
            }
        }

        stage('Build Release APK & AAB') {
            steps {
                sh './gradlew assembleRelease bundleRelease'
            }
        }

        stage('Copy Artifacts to Desktop') {
            steps {
                sh """
                    cp app/build/outputs/apk/debug/*.apk ${DESKTOP_BUILD_DIR}/
                    cp app/build/outputs/apk/release/*.apk ${DESKTOP_BUILD_DIR}/
                    cp app/build/outputs/bundle/release/*.aab ${DESKTOP_BUILD_DIR}/
                """
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
            // Archive all APKs and AABs in Jenkins
            archiveArtifacts artifacts: '**/build/outputs/**/*.apk, **/build/outputs/**/*.aab', fingerprint: true
        }
        failure {
            echo '❌ Build Failed!'
        }
    }
}
