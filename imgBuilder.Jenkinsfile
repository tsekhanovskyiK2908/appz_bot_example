def buildNumber = env.BUILD_NUMBER as int
if (buildNumber > 1) milestone(buildNumber - 1)
milestone(buildNumber)

def dockerImage;

node('dockerImgBuilder'){
	stage('SCM'){
		checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/tsekhanovskyiK2908/appz_bot_example']]]);
	}
	stage('build'){
		dockerImage = docker.build('ktsekhanovskyi/jenkins-maven:latest', './JenkinsMaven');
	}
	stage('push'){
        docker.withRegistry('https://index.docker.io/v1/', 'dockerhubcreds'){
			dockerImage.push();
        }
	}
}