#!/usr/bin/env groovy

def call(String repository, String branch, String directory) {
    def exts = (directory != '' && directory != '.') ? [$class: 'SparseCheckoutPaths', SparseCheckoutPaths:[[$class: 'SparseCheckoutPath', path: directory]]] : null
    checkout([
        $class: 'GitSCM',
        branches: [[name: "*/${branch}"]],
        doGenerateSubmoduleConfigurations: false,
        userRemoteConfigs: [[url: "${repository}"]],
        extensions: exts
    ])
}