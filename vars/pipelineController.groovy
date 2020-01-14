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
    println("getting called")
}

