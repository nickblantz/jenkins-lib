#!/usr/bin/env groovy

import com.qaas.pipeline.JenkinsConfig

def call(environment) {
	def language = JenkinsConfig.getLanguage()
	def envTag = JenkinsConfig.getEnvironmentType(environment)
	def fnTags = JenkinsConfig.getEnvironmentStages(environment)
	def invalidTag = 'ToDo'
	
	//for each functional tag specified in jenkins-config.yml for given environment
	for(tag in fnTags) {
		//creates a jenkins stage
		stage(tag) {
			//tagging logic for the stage
			def tagLogic = "@${envTag} and @${tag} and not @${invalidTag}"
			def isFailed = false
			//list of feature files to be run
			def features = []
			//hash of builds to be executed
			def builds = [:]
			
			//populates the features list with the dry-run results
			node(language) {
				switch(language) {
					case 'ruby':
						rubyDryRun(tagLogic)
						break
					case 'junit':
						javaDryRun(tagLogic)
						break
					case 'specflow':
						cSharpDryRun(tagLogic)
						break
					case 'python':
						pythonDryRun(tagLogic)
						break
					default:
						error('Project language from jenkins-config.yml is not yet supported')
						break
				}
				for(feature in readJSON(file: 'dry-run.json')) {
					features << feature.uri
				}
			}
			
			def stageResult = "Pipeline executing features matching tags: '${tagLogic}'"
			//for each feature in the features list
			for(feature in features) {
				def featureFile = feature
				def featureName = feature.substring(feature.lastIndexOf('/') + 1, feature.lastIndexOf('.'))
				//unique name for feature
				def runName = "${featureName}-${environment}-${tag}"
				
				//populates the builds hash with a build for each feature
				builds[featureName] = {
					node(language) {
						//runs the cucumber tests and stores results
						def buildSuccess
						switch(language) {
							case 'ruby':
								buildSuccess = rubyExecute(tagLogic, featureFile, runName, environment)
								break
							case 'junit':
								buildSuccess = javaExecute(tagLogic, featureFile, runName, environment)
								break
							case 'specflow':
								buildSuccess = cSharpExecute(tagLogic, featureFile, runName, environment)
								break
							case 'python':
								buildSuccess = pythonExecute(tagLogic, featureFile, runName, environment)
								break
							default:
								error('Project language from jenkins-config.yml is not yet supported')
								break
						}
						
						//preparing report JSON for ELK consumption
						try {
							modifyJSON(runName, tagLogic)
							archiveArtifacts(artifacts: "reports/${runName}.json")
						} catch(java.io.FileNotFoundException e1) {
							buildSuccess = false;
							println("Report JSON for ${featureName} did not get generated")
						}
						
						//printing pass/fail for feature
						if(buildSuccess) {
							stageResult += "\r\n SUCCESS: ${featureFile}"
						} else {
							isFailed = true;
							stageResult += "\r\n FAILURE: ${featureFile}"
						}
					}
				}			
			}
			//sends the build hash to be executed
			parallel(builds)
			
			if(isFailed) {
				currentBuild.setResult('FAILURE')
				error(stageResult)
			} else {
				println(stageResult)
			}
		}
		
	}
}

def rubyDryRun(tagLogic) {
	
	if (isUnix()) {
		withEnv(['Path+RUBY=C:/Ruby25-x64/bin']) {
			sh(script: "cucumber --dry-run --tags '${tagLogic}' --format json --out dry-run.json")
		}
	} else {
		withEnv(['Path+RUBY=C:/Ruby25-x64/bin']) {
			bat(script: "cucumber --dry-run --tags '${tagLogic}' --format json --out dry-run.json")
		}
	}
}

def rubyExecute(tagLogic, featureFile, runName, environment) {	
	if (isUnix()) {
		withEnv(['Path+RUBY=C:/Ruby25-x64/bin']) {
			return sh(script: "cucumber --tags '${tagLogic}' '${featureFile}' --format json --out 'reports/${runName}.json' TEST_ENV=${environment}", returnStatus: true) == 0
		}
	} else {
		withEnv(['Path+RUBY=C:/Ruby25-x64/bin']) {
			return bat(script: "cucumber --tags '${tagLogic}' '${featureFile}' --format json --out 'reports/${runName}.json' TEST_ENV=${environment}", returnStatus: true) == 0
		}
	}
}

def javaDryRun(tagLogic) {
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

def javaExecute(tagLogic, featureFile, runName, environment) {
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

def cSharpDryRun(tagLogic) {
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

def cSharpExecute(tagLogic, featureFile, runName, environment) {
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

def pythonDryRun(tagLogic) {
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

def pythonExecute(tagLogic, featureFile, runName, environment) {
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
