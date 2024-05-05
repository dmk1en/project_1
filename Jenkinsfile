node {
 stage('SCM') {
 git branch: 'main', credentialsId: 'dmk1en-github', url: 'https://github.com/dmk1en/project_1.git'
 }
 stage('SonarQube Analysis') {
 def scannerHome = tool 'SonarQube Scanner';
 withSonarQubeEnv() {
 sh "${scannerHome}/bin/sonar-scanner -Dsonar.java.binaries=. -Dsonar.projectKey=project_1 -Dsonar.login=sqa_7f57c38f49cb343684bd223ceca43195173c5abc"
 }
 }
}
