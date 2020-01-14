#!/usr/bin/env groovy

import com.qaas.pipeline.LangCmd

def call(LangCmd cmd, String compName, int execNum) {
    switch cmd {
        case LangCmd.PREPARE_EXECUTOR_1:
        case LangCmd.PREPARE_EXECUTOR_N:
        case LangCmd.TEST_FEATURE:
    }
}

def prepareExecutor1() {
    
}

def prepareExecutorN() {

}

def testFeature() {
    
}