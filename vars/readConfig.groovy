import com.qaas.pipeline.JenkinsConfig

def call(configLocation) {
	new JenkinsConfig(readYaml(file: configLocation))
}
