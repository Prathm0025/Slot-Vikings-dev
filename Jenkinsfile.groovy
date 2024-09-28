def PROJECT_NAME = "Slot-Vikings-dev"
def UNITY_VERSION = "2022.3.48f1"
def UNITY_INSTALLATION = "C:\\Program Files\\Unity\\Hub\\Editor\\${UNITY_VERSION}\\Editor\\Unity.exe"
def REPO_URL = "https://github.com/Prathm0025/Slot-Vikings-dev.git"
def S3_BUCKET = "vikingsbucket"

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
        AWS_ACCESS_KEY = credentials('AWS_ACCESS_KEY')
        AWS_SECRET_KEY = credentials('AWS_SECRET_KEY')
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

        stage('Push Build to GitHub') {
            steps {
                script {
                    dir("${PROJECT_PATH}") { // Ensure you are in the correct directory
                        bat '''
                            git init
                            git config user.email "moreprathmesh849@gmail.com"
                            git config user.name "Prathm0025"
                            git add Builds
                            git status --porcelain
                            git commit -m "Add build"
                            git branch main
                            git remote set-url origin https://${Token}@github.com/Prathm0025/Slot-Vikings-dev.git
                            git push origin main --force
                        '''
                    }
                }
            }
        }

        stage('Deploy to S3') {
            steps {
                script {
                    withEnv(["AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY}", "AWS_SECRET_ACCESS_KEY=${AWS_SECRET_KEY}"]) {
                        dir("${PROJECT_PATH}") {
                            bat '''
                            # Copy all files, including .html files, to S3
                            aws s3 cp "Builds/WebGL/" s3://${S3_BUCKET}/ --recursive --acl public-read
                            
                            # Move index.html to the root for S3 hosting
                            aws s3 cp "Builds/WebGL/index.html" s3://${S3_BUCKET}/index.html --acl public-read
                            
                            # Optional: Set S3 bucket for static web hosting
                            aws s3 website s3://${S3_BUCKET}/ --index-document index.html --error-document index.html
                            '''
                        }
                    }
                }
            }
        }
    }
}
