# Slot Vikings Dev Project

## Prerequisites

### 1. Install Java 21
- Download Java 21 for Windows from the following link:
  - [Download Java 21](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe)
- Set up Java 21:
  - Install Java in: `C:\Program Files\Java\jdk-21\`

### 2. Install Jenkins on Windows
- Download Jenkins for Windows from the following link:
  - [Download Jenkins](https://www.jenkins.io/download/thank-you-downloading-windows-installer-stable)
- Install Jenkins in: `C:\Program Files\Jenkins\`
- Set up Jenkins Server:
  - Access Jenkins at: `http://<your-ip>:8080`
  
### 3. Install Required Jenkins Plugins
- Pipeline Stage View
- Hidden Parameter
- Pipeline: Groovy
- Pipeline: GitHub Groovy Libraries
- Pipeline: Groovy Libraries

### 4. Configure Jenkins Node (Windows)
- Create a Windows node in Jenkins and connect it for executing Unity builds.

### 5. Configure GitHub Webhook
- Go to your GitHub repository settings and add a Jenkins webhook for build triggers.

### 6. Install Unity and Unity Hub
- Download Unity Hub from the following link:
  - [Download Unity Hub](https://public-cdn.cloud.unity3d.com/hub/prod/UnityHubSetup.exe)
- Install Unity Hub in: `C:\Program Files\Unity Hub`
- Open Unity Hub and install the Unity Editor (version 2022.3.47f1):
  - Path to Unity Editor: `C:\Program Files\Unity\Hub\Editor\2022.3.47f1\Editor\Unity.exe`
