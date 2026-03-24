package femtioprocent.logger

object Version {
    fun info() : String {
	return "$projectVersion $buildHost $buildTimestamp ($buildNumber)"
    }
    const val projectGroup: String = "femtioprocent"
    const val projectName: String = "logger"
    const val projectVersion: String = "0.0.1.0"
    const val buildTimestamp: String = "2026-03-24 14:42:14 +0100"
    const val buildHost = "melon.local"
    const val buildNumber = "1013"
}
