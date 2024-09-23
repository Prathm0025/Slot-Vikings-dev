pipeline {
    agent any

    environment {
        Token = credentials('GITHUB_TOKEN')  // Fetch GitHub token from Jenkins credentials
    }

    stages {
        stage('Clone Repository') {
            steps {
                git url: 'https://github.com/Prathm0025/TypeScript-Build.git', branch: 'master'
            }
        }

        stage('Setup Environment') {
            steps {
                script {
                    // Install dependencies
                    sh 'npm install'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Build the project and handle errors
                    catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                        sh 'npm run build --verbose'
                    }
                }
            }
        }

        stage('Run Compiled Output') {
            when {
                expression { currentBuild.currentResult == 'SUCCESS' }
            }
            steps {
                script {
                    // Run the compiled project
                    sh 'node dist/index.js' // Update if necessary, based on your output directory
                }
            }
        }

        stage('Push Artifact') {
            when {
                expression { currentBuild.currentResult == 'SUCCESS' }
            }
            steps {
                script {
                    // Configure Git with user details
                    sh 'git config user.email "you@example.com"'
                    sh 'git config user.name "Your Name"'
                    sh 'git remote set-url origin https://${Token}@github.com/Prathm0025/TypeScript-Build.git'
                    sh 'git add dist/*' // Add your build artifacts from the correct folder
                    sh 'git commit -m "Add new build artifacts"'
                    sh 'git push origin master'
                }
            }
        }
/*
        stage('Rollback') {
            when {
                expression { currentBuild.currentResult == 'FAILURE' }
            }
            steps {
                script {
                    // Perform rollback actions here
                    echo 'Rolling back to the previous build...'
                    sh 'git checkout HEAD^' // Checkout the previous commit
                }
            }
        }
        */
    }

    triggers {
        pollSCM('H/5 * * * *') // Poll SCM every 5 minutes
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '2')) // Keep the last 2 builds
    }
}
