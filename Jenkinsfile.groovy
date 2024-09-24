def PROJECT_NAME = "Slot-Vikings-dev"
def UNITY_VERSION = "2022.3.47f1"
def UNITY_INSTALLATION = "C:\\Program Files\\Unity\\Hub\\Editor\\${UNITY_VERSION}\\Editor"
def REPO_URL = "https://github.com/Prathm0025/Slot-Vikings-dev.git"
def LOG_FILE_PATH = "build.log" // Define the log file path relative to the workspace

pipeline {
    agent any // Use any available agent
    
    environment {
        PROJECT_PATH = "C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\Viking-build\\" // Define project path based on Jenkins workspace
        Token = credentials('GITHUB_TOKEN') // Use GitHub credentials
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Clean workspace before cloning
                    deleteDir()
                    // Clone the repository
                    git url: REPO_URL, branch: 'dev-build'
                }
            }
        }

        stage('Build WebGL') {
            steps {
                script {
                    // Ensure the Unity path is correct and does not use workspace path
                    withEnv(["UNITY_PATH=${UNITY_INSTALLATION}"]) {
                        bat '''
                        "%UNITY_PATH%\\Unity.exe" -quit -batchmode -projectPath "%PROJECT_PATH%" -executeMethod BuildScript.BuildWebGL -logFile -
                        '''
                    }
                }
            }
        }

        stage('Push Build') {
            steps {
                script {
                    dir("${PROJECT_PATH}\\Builds\\WebGL") {
                        // Ensure you are in the build directory
                        bat '''
                        git init
                        git config user.email "you@example.com"
                        git config user.name "Your Name"
                        git add .
                        git commit -m "Add WebGL build"
                        git add "%WORKSPACE%\\%LOG_FILE_PATH%" // Add the log file to the commit
                        git commit -m "Add build log"
                        git remote add origin ${REPO_URL}
                        git push origin dev-build --force // Push to the correct branch
                        '''
                    }
                }
            }
        }
    }
}
