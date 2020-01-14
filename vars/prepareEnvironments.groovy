#!/usr/bin/env groovy

import com.qaas.pipeline.BuildEnv
import com.qaas.pipeline.LangCmd
import hudson.model.labels.LabelAtom

def call() {
    checkoutSource(params.repository_url, params.branch, params.directory)
	try {
        BuildEnv.initialize(
            env.JOB_BASE_NAME, 
            params.environment, 
            params.repository_url, 
            params.branch, 
            params.directory, 
            readYaml(file: 'jenkins-config.yml')
        )
    } catch (err) {
        // level: severe
        // notify: all
        // message: the following data is missing from your jenkins-config.yml: [language, env::QA1::env_type, ...]
    }
    prepareNodes()
}

def prepareNodes() {
    Map builds = [:]
    LabelAtom[] requiredLabels = BuildEnv.getLanguage().split(" && ").sort().collect({ name -> LabelAtom(name)})
    for (computer in Jenkins.instance.computers) {
        if (computer.isOnline() && computer.getAssignedLabels().sort().containsAll(requiredLabels)) {
            for (int i = computer.getNumExecutors(); i > 0; i--) {
                String compName = computer.getName()
                int execNum = i
                builds["${compName}-${execNum}"] = { prepareExecutor(compName, execNum) }
            }
        }
    }
}

def prepareExecutor(String compName, int execNum) {
    switch BuildEnv.getLanguage() {
        case 'rust':
            if (execNum == 1) rust(LangCmd.PREPARE_EXECUTOR_1, compName, execNum)
            else rust(LangCmd.PREPARE_EXECUTOR_N, compName, execNum)
        case default:
            error("'${BuildEnv.getLanguage()}' is not an valid language")
    }
}