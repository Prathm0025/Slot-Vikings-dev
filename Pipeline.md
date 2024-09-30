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

# Creating the Jenkins Pipeline

Follow these steps to create and configure the Jenkins pipeline for the Slot Vikings Dev project:

### Step 1: Access Jenkins
- Open your Jenkins server by navigating to: `http://<your-ip>:8080`
- Log in with your Jenkins credentials.

### Step 2: Create a New Job
- On the Jenkins dashboard, click **New Item**.
- Enter a name for your pipeline (e.g., "Slot-Vikings-Pipeline").
- Select **Pipeline** as the job type.
- Click **OK** to proceed.

### Step 3: Configure General Settings
- In the **General** tab, you can provide a description of the project.
- If your repository is hosted on GitHub, check the box for **GitHub project** and add the repository URL.

### Step 4: Configure Pipeline Source
- Scroll down to the **Pipeline** section.
- Select **Pipeline script from SCM** (Source Code Management).
- In the **SCM** dropdown, choose **Git**.
- In the **Repository URL**, enter your GitHub repository URL (e.g., `https://github.com/Prathm0025/Slot-Vikings-dev.git`).
- In the **Branches to build**, enter `dev-build` (or any other branch you want to use for builds).
- In the **Credentials** section, select the credentials you added earlier for GitHub authentication.

### Step 5: Define Jenkinsfile Path
- Under the **Script Path**, ensure the field is set to the correct file location of your `Jenkinsfile.groovy`. The default is `Jenkinsfile.groovy` if it is located in the root of your repository.

### Step 6: Configure Build Triggers (Optional)
- If you want to trigger builds automatically, configure triggers under the **Build Triggers** section.
- Enable **GitHub hook trigger for GITScm polling** to automatically trigger builds when changes are pushed to the repository.

### Step 7: Save the Job
- Once the pipeline configuration is complete, click **Save**.

### Step 8: Trigger the Pipeline
- You can manually trigger the pipeline by clicking **Build Now** on the project page.
- If everything is set up correctly, the pipeline will automatically pull the code, build the Unity WebGL version, push the build to GitHub, and deploy it to your S3 bucket.

---
