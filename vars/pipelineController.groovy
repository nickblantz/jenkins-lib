#!/usr/bin/env groovy

import com.qaas.pipeline.Status

def call() {
    checkoutSource(params.repository_type, params.repository_url, params.repository_branch)
    Map instructionMap = loadInstruction(params.path_to_instructions)
    Pipeline pipeline = new Pipeline(instructionMap)

    switch stepController(pipeline)

}

def stepController(Pipeline pipeline) {
    Map vars = pipeline.getVariables()
    AbstractStep[] steps = pipeline.getSteps()
    Result currentResult = new Result status: Status.SUCCESS
    for(step in steps) {
        Result stepResult = evaluateStep(step, vars, currentResult.getStatus())
        switch stepResult.getStatus() {
            case Status.SUCCESS:
                // continue
            case Status.UNSTABLE:
                // pipeline.status = Status.UNSTABLE
            case Status.FAILURE:
                // overallResult = stepResult
                // skip remaining steps
                // 
            case Status.ABORTED:
                // skip remaining steps
            case Status.SKIPPED:
                // skip remaining steps
        }

    }
}

def evaluateStep(AbstractStep step, Map vars, Status priorStatus) {
    switch priorStatus {
        case Status.SUCCESS:
            // actually evaluate the step
        default:
            return new Result result: Status.SKIPPED
    }
}