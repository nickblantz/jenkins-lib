#!/usr/bin/env groovy

// def call(
//     String repoType, 
//     String repoURL, 
//     String repoBranch, 
//     String pathToInstructionYML
//     String credentialsID
// ) {
//     checkoutSource(repoType, repoURL, repoBranch, pathToInstructionYML, credentialsID)
//     def instructionYML = readInstructions(pathToInstructionYML)
//     def pipelineVars = instructionYML['variables']
//     def pipelineSteps = instructionYML['steps']
//     def pipelinePost = instructionYML['post']
    
// }

def call () {
    // Required Pipeline Parameters
    properties([parameters([
        string(defaultValue: '', description: 'Name of the environment under test', name: 'repository_type'),
        string(defaultValue: '', description: 'Url of the test source code repository', name: 'repository_url'),
        string(defaultValue: '', description: 'Branch of the test source code repository', name: 'branch'),
        string(defaultValue: '', description: 'Directory in the test source code branch', name: 'directory')
    ])])
    println("getting called")
}

