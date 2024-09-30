# Slot Vikings Dev Project

This document outlines the prerequisites and setup instructions for running the Slot Vikings Dev project pipeline on Jenkins.

## Prerequisites

### 1. Install Java 21
To run Jenkins, you'll need Java 21 installed on your system. Follow these steps to install Java:

- Download Java 21 for Windows:
  - [Download Java 21](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe)
- Install Java in the following location:
  - `C:\Program Files\Java\jdk-21\`

### 2. Install Jenkins on Windows
Jenkins is the tool used to automate the build and deployment process for this project. Install Jenkins on your Windows machine:

- Download Jenkins for Windows:
  - [Download Jenkins](https://www.jenkins.io/download/thank-you-downloading-windows-installer-stable)
- Install Jenkins in:
  - `C:\Program Files\Jenkins\`
- Once installed, start the Jenkins server and access it at:
  - `http://<your-ip>:8080`

### 3. Install Required Jenkins Plugins
To ensure the pipeline runs smoothly, you need to install the following Jenkins plugins:

- **Pipeline Stage View**: Provides visualization for pipeline stages.
- **Hidden Parameter**: Allows you to define hidden parameters in pipelines.
- **Pipeline: Groovy**: Provides Groovy scripting support for Jenkins pipelines.
- **Pipeline: GitHub Groovy Libraries**: Integrates GitHub libraries into your pipeline.

### 4. Configure Jenkins Node (Windows)
You need to configure a Windows-based Jenkins node to execute Unity builds:

- In Jenkins, navigate to **Manage Jenkins > Manage Nodes and Clouds**.
- Create a new node, assign it a name, and configure it to run on your Windows machine.
- Ensure the node is connected properly and able to run jobs.

### 5. Configure GitHub Webhook
To trigger builds automatically, set up a GitHub webhook in your repository:

- Go to your GitHub repository (e.g., `https://github.com/Prathm0025/Slot-Vikings-dev`).
- Navigate to **Settings > Webhooks > Add Webhook**.
- Enter the Jenkins URL in the **Payload URL** field: `http://<your-ip>:8080/github-webhook/`
- Set the **Content type** to `application/json` and select the trigger events for the webhook.

### 6. Install Unity and Unity Hub
Install Unity and Unity Hub on your Windows machine to build the project:

- Download Unity Hub:
  - [Download Unity Hub](https://public-cdn.cloud.unity3d.com/hub/prod/UnityHubSetup.exe)
- Install Unity Hub in:
  - `C:\Program Files\Unity Hub\`
- Use Unity Hub to install the Unity Editor version **2022.3.48f1**:
  - Path to Unity Editor: `C:\Program Files\Unity\Hub\Editor\2022.3.47f1\Editor\Unity.exe`
  
After completing these steps, your system will be ready to run the Slot Vikings Dev project pipeline using Jenkins.
