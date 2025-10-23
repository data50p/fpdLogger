package femtioprocent.logger

object Version {
    fun info() : String {
	return "$projectVersion $buildHost $buildTimestamp ($buildNumber)"
    }
    const val projectGroup: String = "femtioprocent"
    const val projectName: String = "logger"
    const val projectVersion: String = "0.0.1.0"
    const val buildTimestamp: String = "2025-10-23 12:59:59 +0200"
    const val buildHost = "apelsin-3.local"
    const val buildNumber = "1004"
}
