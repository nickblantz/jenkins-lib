#!/usr/bin/env groovy

def call(repository, branch, configLocation) {
    gitClone(repository, branch)
	readConfig(configLocation)
}
