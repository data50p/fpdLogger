package femtioprocent.logging

import femtioprocent.sundry.Ansi
import femtioprocent.sundry.Ansi.Color
import femtioprocent.sundry.Sundry
import java.io.File
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.*
import java.util.logging.Formatter
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class LogManager(val name: String, val colorFun: ((String) -> String)? = null) {

    lateinit var loggingHandler: MyHandler

    private inner class MyFormatter : Formatter() {
	private val maxWidth = 26

	private inner class LastState() {
	    var value: Long = Sundry.ct()
	    var currentValue: Long = 0
	    var count: Int = 0
	    var lastValue = Sundry.ct()

	    fun update(newValue: Long): Long {
		if (newValue == currentValue) {
		    if (count < 999)
			count++
		} else {
		    count = 0
		    currentValue = newValue
		}

		lastValue = value
		value = newValue

		return delta
	    }

	    val delta: Long
		get() {
		    return value - lastValue
		}
	}

	val recordTimeLastState = LastState()

	override fun format(record: LogRecord): String {
	    fun limited(s: String, max: Int): String = if (s.length > max - 1) s.take(max - 1) + "…" else s

	    val sourceClassName = record.getSourceClassName().let { it.substring(it.lastIndexOf('.') + 1) }
	    val sourceMethodName = record.getSourceMethodName()
	    val threadId = record.longThreadID
	    val sequence = record.sequenceNumber

	    val more = StringBuilder()
	    record.thrown?.stackTrace?.forEach {
		more.append(" ")
		more.append(it)
	    }

	    if (true) {
		val prefix = if (this@LogManager.colorFun != null) colorFun(this@LogManager.name) else this@LogManager.name
		(if (this@LogManager.name == "stderr") System.err else System.out).println("$prefix ${Ansi.fg(Color.C, record.message)}")
	    }

	    var rtV: Long? = null
	    var rtD: Long? = null
	    synchronized(recordTimeLastState) {
		recordTimeLastState.update(record.millis)
		rtV = recordTimeLastState.value
		rtD = recordTimeLastState.delta
	    }

	    val recordFormattedTime = dateFormat.format(Date(rtV!!))
	    val ret = "" +
		      Sundry.padLeft("" + sequence, 9, '0') + ' ' +
		      Ansi.fg(levelColor(record.level), Sundry.padRight("" + record.level, 10, ' ')) +
		      recordFormattedTime + ' ' +
		      Sundry.padLeft("÷$threadId", 6, ' ') + ' ' +
		      Sundry.padLeft("" + rtD, 5, ' ') + ' ' +
		      Sundry.padRight("" + limited(sourceClassName, maxWidth), maxWidth, ' ') + ' ' +
		      Sundry.padRight("" + limited(sourceMethodName, maxWidth), maxWidth, ' ') + ' ' +
		      record.message + more + '\n'
	    return ret

	}
    }

    companion object {
	var dateFormat: DateFormat = SimpleDateFormat("dd/MM HH:mm:ss.SSS")
    }

    fun levelColor(level: Level): Color {
	return when (level) {
	    Level.SEVERE  -> Color.R
	    Level.WARNING -> Color.O
	    Level.INFO    -> Color.Y
	    Level.CONFIG  -> Color.GD
	    Level.FINE    -> Color.CD
	    Level.FINER   -> Color.C
	    Level.FINEST  -> Color.CL
	    else          -> Color.W
	}
    }

    private val myFormatter = MyFormatter()

    var logger: Logger? = null

    fun setLevel(level: Level) {
	logger?.level = level
	loggingHandler.level = level
    }

    class MyHandler(val name: String) : FileHandler("logs/$name") {
	override fun publish(record: LogRecord?) {
	    try {
		super.publish(record)
	    } catch (ex: Exception) {
		System.err.println("Can't publish: $name $record $ex")
	    }
	}
    }

    init {
	try {
	    Logger.getLogger(name)?.let { logger ->
		//         logger.getParent().setLevel(Level.OFF);
		this.logger = logger
		logger.setLevel(Level.ALL)
		val d = File("logs")
		d.mkdir()
		loggingHandler = MyHandler(name)
		loggingHandler.formatter = myFormatter
		logger.addHandler(loggingHandler)
		logger.useParentHandlers = false
		//            logger.setUseParentHandlers(false);
	    }
	} catch (ex: IOException) {
	    System.err.println("ex: $ex")
	} catch (ex: NoClassDefFoundError) {
	    System.err.println("ex: $ex")
	    logger = null
	}
    }
}

fun interface Loggger {
    fun log(line: String)
}

val consoleLogger = Loggger { println(it) }

@OptIn(ExperimentalTime::class)
fun Loggger.withDateTime(clock: Clock = Clock.System) = Loggger { log("Time:${clock.now()}: $it") }
fun Loggger.withThreadName() = Loggger { log("Thread:${Thread.currentThread().name}: $it") }

@OptIn(ExperimentalTime::class)
val defaultConsoleLogger = consoleLogger.withDateTime().withThreadName()

@OptIn(ExperimentalTime::class)
fun main() {
    defaultConsoleLogger.log("Hello World")
}