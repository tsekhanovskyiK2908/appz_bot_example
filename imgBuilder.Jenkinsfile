def dockerImage;

node('docker'){
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