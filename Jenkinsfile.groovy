def PROJECT_NAME = "Slot-Vikings-dev"
def UNITY_VERSION = "2022.3.48f1"
def UNITY_INSTALLATION = "C:\\Program Files\\Unity\\Hub\\Editor\\${UNITY_VERSION}\\Editor\\Unity.exe"
def REPO_URL = "https://github.com/Prathm0025/Slot-Vikings-dev.git"

pipeline {
    agent {
        label 'windows' // Specify the agent by label
    }

    options {
        timeout(time: 20, unit: 'MINUTES') // Set a timeout for the entire build
    }
    
    environment {
        PROJECT_PATH = "C:\\${PROJECT_NAME}" 
        Token = credentials('GITHUB_Prathm0025') 
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    dir("${PROJECT_PATH}") { 
                         git url: REPO_URL, branch: 'dev-build', credentialsId: 'GITHUB_Prathm0025'
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
                            git init
                            git config user.email "moreprathmesh849@gmail.com"
                            git config user.name "Prathm0025"
                            git add Builds
                            git status --porcelain
                            IF ERRORLEVEL 1 (
                            git commit -m "Add build"
                            git branch main
                            git remote set-url origin https://${Token}@github.com/Prathm0025/Slot-Vikings-dev.git
                            git push origin main --force
                            ) ELSE (
                            echo No changes to commit
                            )
                        '''
                    }
                }
            }
        }
        
        stage('Deploy to S3') {
            steps {
                script {
                    bat '''
                    aws s3 cp "Builds/WebGL/" s3://vikingsbucket/ --recursive --exclude "*.html" --acl public-read
                    '''
                }
            }
        }
    }
}
