def PROJECT_NAME = "Slot-Vikings-dev"
def UNITY_VERSION = "2022.3.47f1"
def UNITY_INSTALLATION = "C:\\Program Files\\Unity\\Hub\\Editor\\${UNITY_VERSION}\\Editor\\Unity.exe"
def REPO_URL = "https://github.com/Prathm0025/Slot-Vikings-dev.git"
def LOG_FILE_PATH = "build.log" // Define the log file path relative to the workspace

pipeline {
    agent {
        label 'Windows-Agent' // Specify the agent by label
        customWorkspace 'C:\\Jenkins\\Unity_Projects\\Slot-Vikings-dev' // Use the custom workspace
    }
    
    environment {
        PROJECT_PATH = "C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\Viking-build" // Define project path based on Jenkins workspace
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
                    // Ensure the Unity path is correct
                    withEnv(["UNITY_PATH=${UNITY_INSTALLATION}"]) {
                        bat '''
                        "%UNITY_PATH%" -quit -batchmode -projectPath "%PROJECT_PATH%" -executeMethod BuildScript.BuildWebGL -logFile "%WORKSPACE%\\%LOG_FILE_PATH%"
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
                        git config user.email "moreprathamesh849@gmail.com"
                        git config user.name "Prathm0025"
                        git add .
                        git commit -m "Add WebGL build"
                        git remote add origin ${REPO_URL}
                        git push origin dev-build --force // Push to the correct branch
                        '''
                    }
                }
            }
        }
    }
}
