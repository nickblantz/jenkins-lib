#!/usr/bin/env groovy

def call(runName, tagLogic) {
	def reportJSON = readJSON(file: "reports/${runName}.json")
	if (reportJSON.size() > 0) {
		reportJSON[0]['name'] += " [Tags: ${tagLogic}]"
		reportJSON[0]['timestamp'] = new Date().toTimestamp().toString()
		writeJSON(file: "reports/${runName}.json", json: reportJSON)
	}
	if (isUnix()) {
		sh("echo.>>reports/${runName}.json")
	} else {
		bat("echo.>>reports/${runName}.json")
	}
}