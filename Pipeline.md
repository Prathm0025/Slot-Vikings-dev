# Jenkins Pipeline Overview for Slot-Vikings-dev

This document provides an overview of the Jenkins pipeline used for automating Unity WebGL builds and deploying them to AWS S3 for hosting. The pipeline file (`Jenkinsfile`) is already included in the repository.

## Pipeline Stages

### 1. **Checkout Stage**
   - Clones the `dev-build` branch of the Slot-Vikings-dev repository.
   - Ensures that the latest code from the repository is used for the build process.

### 2. **Build WebGL Stage**
   - Uses Unity in batch mode to build the WebGL version of the project.
   - The Unity build process is executed using a custom method (`BuildScript.BuildWebGL`) that must be defined in the Unity project.

### 3. **Push Build to GitHub Stage**
   - Initializes a new Git repository if one doesn't exist.
   - Configures Git user details and commits the WebGL build files to the local repository.
   - Pushes the build to the `main` branch of the repository with forced updates.

### 4. **Deploy to S3 Stage**
   - Uploads the WebGL build files to a specified AWS S3 bucket.
   - Moves the `index.html` to the root of the S3 bucket to enable static website hosting.
   - Configures the S3 bucket for hosting the WebGL build by setting the `index.html` as the main entry point.

## Setup Requirements and Modifications

If you want to run this pipeline, the following setup steps and modifications are required:

### 1. **Jenkins Agent**
   - Ensure you have a Jenkins agent (preferably Windows) available that can execute Unity builds.
   - The agent should have:
     - Git installed.
     - Unity installed in the path defined in the pipeline (`C:\\Program Files\\Unity\\Hub\\Editor\\{UNITY_VERSION}\\Editor\\Unity.exe`).
     - AWS CLI installed and configured with the necessary permissions to upload to S3.

### 2. **GitHub Credentials**
   - Add your GitHub credentials in Jenkins as a secret text or token.
   - In the Jenkinsfile, replace the `credentials('GITHUB_Prathm0025')` with your own credential ID for GitHub authentication.

### 3. **S3 Bucket**
   - Replace the S3 bucket name in the `S3_BUCKET` environment variable with your own bucket name.
   - Ensure that the bucket is correctly set up for static website hosting.

### 4. **Unity Build Script**
   - Ensure that a custom Unity build script (`BuildScript.cs`) is created in your Unity project. This script should contain a method to build the WebGL version of your project.

   Example build script:
   ```csharp
   using UnityEditor;

   public class BuildScript
   {
       public static void BuildWebGL()
       {
           string[] scenes = { "Assets/Scenes/MainScene.unity" };
           BuildPipeline.BuildPlayer(scenes, "Builds/WebGL", BuildTarget.WebGL, BuildOptions.None);
       }
   }
