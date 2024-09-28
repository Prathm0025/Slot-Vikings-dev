def PROJECT_NAME = "Slot-Vikings-dev"
def UNITY_VERSION = "2022.3.48f1"
def UNITY_INSTALLATION = "C:\\Program Files\\Unity\\Hub\\Editor\\${UNITY_VERSION}\\Editor\\Unity.exe"
def REPO_URL = "https://github.com/prathammore0025/Slot-Vikings-dev.git"

pipeline {
    agent {
        label 'windows' // Specify the agent by label
    }
    
    environment {
        PROJECT_PATH = "C:\\${PROJECT_NAME}" // Cloning the repository into C:/ instead of C:/Program Files
        Token = credentials('GITHUB') // Use GitHub credentials
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    dir("${PROJECT_PATH}") { // Clone into the desired directory
                         git url: REPO_URL, branch: 'dev', credentialsId: 'GITHUB'
                    }
                }
            }
        }

        stage('Build WebGL') {
            steps {
                script {
                    withEnv(["UNITY_PATH=${UNITY_INSTALLATION}"]) {
                        bat '''
                        "%UNITY_PATH%" -quit -batchmode -projectPath "%PROJECT_PATH%" -executeMethod BuildScript.BuildWebGL -logFile -
                        '''
                    }
                }
            }
        }

        stage('Push Build') {
            steps {
                script {
                    dir("${PROJECT_PATH}") { // Ensure you are in the correct directory
                        bat '''
                        git stash 
                        git checkout dev-build
                        git checkout dev -- Builds
                        git add Builds
                        git commit -m "Added Builds folder from dev branch"
                        git config user.email "moreprathmesh849@gmail.com"
                        git config user.name "prathammore0025"
                        git remote set-url origin https://${Token}@github.com/prathammore0025/Slot-Vikings-dev.git
                        git push origin dev-build
                        '''
                    }
                }
            }
        }
        
        stage('Deploy to S3') {
            steps {
                script {
                    bat '''
                    aws s3 cp "Builds\\" s3://vikingsbucket/ --recursive
                    '''
                }
            }
        }
    }
}
