def PROJECT_NAME = "jenkins-unity-test"
def CUSTOM_WORKSPACE = "C:\\Users\\morep\\OneDrive\\Documents\\Practice\\new\\jenkins-unity-test\\${PROJECT_NAME}"
def UNITY_VERSION = "2022.3.47f1"
def UNITY_INSTALLATION = "C:\\Program Files\\Unity\\Hub\\Editor\\${UNITY_VERSION}\\Editor"
def REPO_URL = "https://github.com/Prathm0025/Slot-Vikings-dev.git"
def LOG_FILE_PATH = "${CUSTOM_WORKSPACE}/build.log" // Define the log file path

pipeline {
    agent {
        label {
            label ""
            customWorkspace "${CUSTOM_WORKSPACE}"
        }
    }
    
    environment {
        PROJECT_PATH = "${CUSTOM_WORKSPACE}\\${PROJECT_NAME}"
        Token = credentials('GITHUB_TOKEN')
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Clean workspace before cloning
                    deleteDir()
                    // Clone the repository
                    git url: REPO_URL, branch: 'dev'
                }
            }
        }

        stage('Build WebGL') {
            steps {
                script {
                    withEnv(["UNITY_PATH=${UNITY_INSTALLATION}"]) {
                        bat '''
                        "%UNITY_PATH%/Unity.exe" -quit -batchmode -projectPath %PROJECT_PATH% -executeMethod BuildScript.BuildWebGL -logFile "%LOG_FILE_PATH%"
                        '''
                    }
                }
            }
        }

        stage('Push Build') {
            steps {
                script {
                    dir("${PROJECT_PATH}/Builds/WebGL") {
                        // Ensure you are in the build directory
                        bat '''
                        git init
                        git config user.email "you@example.com"
                        git config user.name "Your Name"
                        git add .
                        git commit -m "Add WebGL build"
                        git add "%LOG_FILE_PATH%" // Add the log file to the commit
                        git commit -m "Add build log"
                        git remote add origin ${REPO_URL}
                        git push origin dev --force
                        '''
                    }
                }
            }
        }
    }
}
