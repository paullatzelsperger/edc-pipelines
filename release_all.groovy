pipeline {
    agent any
    stages {

        stage('runtime-metamodel') {
            steps {
                build job: 'Build-Component-Template', parameters: [string(name: 'REPO', value: 'https://github.com/eclipse-edc/GradlePlugins.git'), string(name: 'VERSION', value: "${VERSION}")]
            }
        }

        stage('connector') {
            steps {
                build job: 'Build-Component-Template', parameters: [string(name: 'REPO', value: 'https://github.com/eclipse-edc/Connector.git'), string(name: 'VERSION', value: "${VERSION}")]
            }
        }

        stage("identityhub") {
            steps {
                build job: 'Build-Component-Template', parameters: [string(name: 'REPO', value: 'https://github.com/eclipse-edc/IdentityHub.git'), string(name: 'VERSION', value: "${VERSION}")]
            }
        }

        stage("registration-service") {
            steps {
                build job: 'Build-Component-Template', parameters: [string(name: 'REPO', value: 'https://github.com/eclipse-edc/RegistrationService.git'), string(name: 'VERSION', value: "${VERSION}")]
            }

        }
        stage("federated-catalog") {
            steps {
                build job: 'Build-Component-Template', parameters: [string(name: 'REPO', value: 'https://github.com/eclipse-edc/FederatedCatalog.git'), string(name: 'VERSION', value: "${VERSION}")]
            }
        }

    }
    // dismissed because this webhook was being used maliciously, that's why discord disabled it. We need to put the url in jenkins credentials
    // post {
    //     always {
    //         build(job: "../DiscordWebhook", parameters: [string(name: "UPSTREAM_JOB_URL", value: "${env.BUILD_URL}"), string(name: "JOB_NAME", value: "${env.JOB_NAME}"), string(name: "BUILD_NUMBER", value: "${env.BUILD_NUMBER}"), string(name: "REPO_URL", value: "https://github.com/eclipse-edc/Connector"), string(name: "CONTENT", value: "Look, I built a nightly version ${VERSION} of the components!"), string(name: "WEBHOOK_URL", value: "https://discord.com/api/webhooks/1044851842754027622/vLIhAy_eTODGk5GZbsYJSV351bQVJPvCGpw57AeJxqIVJ0eN11tlH8FCj3HayXjefyzz")])
    //     }
    // }
}
