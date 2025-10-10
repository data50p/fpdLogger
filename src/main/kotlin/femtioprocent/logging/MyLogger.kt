package femtioprocent.logging

import femtioprocent.sundry.Ansi
import femtioprocent.sundry.Ansi.Color
import java.util.logging.Level
import java.util.logging.Logger

object MyLogger {
    private var logManagers: HashMap<String, LogManager> = HashMap()

    private var logLevel: Level = Level.INFO

    fun setLogLevel(level: String) {
        logLevel = when (level) {
            "OFF"     -> Level.OFF
            "SEVERE"  -> Level.SEVERE
            "WARNING" -> Level.WARNING
            "INFO"    -> Level.INFO
            "CONFIG"  -> Level.CONFIG
            "FINE"    -> Level.FINE
            "FINER"   -> Level.FINER
            "FINEST"  -> Level.FINEST
            "ALL"     -> Level.ALL
            else      -> Level.ALL
        }
        logManagers.forEach { id, log -> log.setLevel(logLevel) }
    }

    private fun theLogger(name: String, colorFun: ((String) -> String)): Logger {
        var logManager = logManagers[name]
        if (logManager == null) {
            logManager = LogManager(name, colorFun)
            logManager.logger?.let {
                it.level = logLevel
            }
            logManagers[name] = logManager
        }
        return logManager.logger!!
    }

    val logOut = theLogger("logOut", Ansi.colorFun(Color.Y))
    val logMonitor = theLogger("logMonitor", Ansi.colorFun(Color.M))

    fun isLevel(l: Level): Boolean {
        return logOut.isLoggable(l)
    }
}