#!/usr/bin/env groovy

import com.qaas.pipeline.JenkinsConfig

def call(repository, branch) {
    echo "Preparing slaves..."
	def language = JenkinsConfig.getLanguage()
	def builds = [:]
	def onlineCounter = 0
	// for each jenkins slave
	for(computer in Jenkins.instance.computers) {
		// if computer has label matching 'language'
		if (computer.isOnline() && computer.getAssignedLabels().findAll({it.name == language}).size() > 0) {
			onlineCounter++
			// for each executor in slave
			for (int executorNum = 0; executorNum < computer.getNumExecutors(); executorNum++) {
				def computerName = computer.getName()
				// creates build corresponding to slave and executor number
				builds["${computerName}_${executorNum}"] = {
					// build on specified node
					// logical error: if 2 pipelines are running at the same time, both use an agent with 
							 // 2+ executors, and pipeline A's test job is not finished before this
					 		 // step takes place, this will prepare the same executor twice
					node(computerName) {
						deleteDir()
						gitClone(repository, branch)
						if (isUnix()) {
							sh('mkdir -p reports')
						} else {
							bat('mkdir reports')
						}
						switch(language) {
							case 'ruby':
								echo 'Provisioning ruby env...'
								rubyPrepare()
								break
							case 'junit':
								echo 'Provisioning java env...'
								javaPrepare()
								break
							case 'specflow':
								echo 'Provisioning c# env...'
								cSharpPrepare()
								break
							case 'python':
								echo 'Provisioning c# env...'
								pythonPrepare()
								break
							default:
								error('Project language from jenkins-config.yml is not yet supported')
								break
						}
					}
				}
			}
		}
	}
	if (onlineCounter > 0) {
		parallel(builds)
	} else {
		error("No online nodes matching label: [${language}]")
	}
}

def rubyPrepare() {
	if (isUnix()) {
		withEnv(['Path+RUBY=C:/Ruby25-x64/bin']) {
			bat("bundle install")
		}
	} else {
		withEnv(['Path+RUBY=C:/Ruby25-x64/bin']) {
			// To-Do
		}
	}
}

def javaPrepare() {
	if (isUnix()) {
		withEnv(['Path+JAVA=C:/Ruby25-x64/bin']) {
			// To-Do
		}
	} else {
		withEnv(['Path+JAVA=C:/Ruby25-x64/bin']) {
			// To-Do
		}
	}
}

def cSharpPrepare() {
	if (isUnix()) {
		withEnv(['Path+CSHARP=C:/Ruby25-x64/bin']) {
			// To-Do
		}
	} else {
		withEnv(['Path+CSHARP=C:/Ruby25-x64/bin']) {
			// To-Do
		}
	}
}

def pythonPrepare() {
	if (isUnix()) {
		withEnv(['Path+PYTHON=C:/Ruby25-x64/bin']) {
			// To-Do
		}
	} else {
		withEnv(['Path+PYTHON=C:/Ruby25-x64/bin']) {
			// To-Do
		}
	}
}
