package femtioprocent.logger

object Version {
    fun info() : String {
	return "$projectVersion $buildHost $buildTimestamp ($buildNumber)"
    }
    const val projectGroup: String = "femtioprocent"
    const val projectName: String = "logger"
    const val projectVersion: String = "0.0.1.0"
    const val buildTimestamp: String = "2025-10-11 12:00:32 +0200"
    const val buildHost = "hallon.local"
    const val buildNumber = "1009"
}
