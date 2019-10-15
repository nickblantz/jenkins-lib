package com.qaas.pipeline

class JenkinsConfig implements Serializable {
	protected static Map dataMap = [:]
	
	public JenkinsConfig(Map dataMap) {
		this.dataMap = dataMap
	}
	
	public static String getLanguage() {
		return dataMap['language'].toString()
	}
	
	public static String getEnvironmentType(String environment) {
		return dataMap['environments'][environment]['env_type']
	}
	
	public static String[] getEnvironmentStages(String environment) {
		return dataMap['environments'][environment]['stages']
	}
	
	public static String[] getEmailList() {
		return dataMap['email']
	}
}