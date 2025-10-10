//
//	$Id: S.java,v 1.3 2000/02/22 09:48:40 lars Exp $
//
package femtioprocent.sundry

import femtioprocent.sundry.Sundry.split
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Convenient functions for the effective programmer.
 *
 *
 * Many tedius task are handled here. This class is expected to be used everywhere.
 */
object Sundry {
    /**
     * Sleep for some milliseconds. Might return before timeout if it is interrupted.
     *
     * @param a how many milliseconds to sleep.
     */
    fun milliSleep(a: Long) {
        try {
            TimeUnit.MILLISECONDS.sleep(a)
        } catch (`_`: InterruptedException) {
        }
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private val pad_blank = CharArray(100)
    private val pad_zero = CharArray(pad_blank.size)

    init {
        Arrays.fill(pad_blank, ' ')
        Arrays.fill(pad_zero, '0')
    }

    fun padCenter(s: String, len: Int, ch: Char) : String {
	val pr = len - (len - s.length + 1) / 2
	return padLeft(padRight(s, pr, ch), len, ch)
    }

    /**
     * Format an integer with a specified width. Pad on left side
     */
    fun padLeft(s: String, len: Int, ch: Char): String {
        val l = len - s.length
        if (l <= 0) {
            return s
        }

        if (l <= pad_blank.size) {
            val chA = when (ch) {
                ' '  -> pad_blank
                '0'  -> pad_zero
                else -> CharArray(l).also { Arrays.fill(it, ch) }
            }
            return String(chA, 0, l) + s
        }
        val chA = when (ch) {
            ' '  -> pad_blank
            '0'  -> pad_zero
            else -> CharArray(l).also { Arrays.fill(it, ch) }
        }
        return padLeft(String(chA) + s, len, ch)
    }

    /**
     * Format an integer with a specified width. Pad on right side
     */
    fun padRight(s: String, len: Int, ch: Char): String {
        val l = len - s.length
        if (l <= 0) {
            return s
        }

        if (l <= pad_blank.size) {
            val chA = when (ch) {
                ' '  -> pad_blank
                '0'  -> pad_zero
                else -> CharArray(l).also { Arrays.fill(it, ch) }
            }
            return s + String(chA, 0, l)
        }
        val chA = when (ch) {
            ' '  -> pad_blank
            '0'  -> pad_zero
            else -> CharArray(l).also { Arrays.fill(it, ch) }
        }
        return padRight(s + String(chA), len, ch)
    }

    fun padRightNum(s: String, len: Int): Int {
        val l = len - s.length
        if (l <= 0) {
            return 0
        }
        if (l <= pad_blank.size) {
            return l
        }
        return len
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - -
    /**
     * return time in milliseconds. Same as System.currentTimeMillis();
     */
    fun ct(): Long {
        return System.currentTimeMillis()
    }

    fun cnt(): Long {
        return System.nanoTime()
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - -
    /**
     * Split a String into a List of Strings.
     *
     * @param str String to split
     * @param split which character to have as split point. Any character in split can be split charachter.
     * @return List of String
     */
    fun split(str: String, split: String): List<String> {
        val t = StringTokenizer(str, split)

        t.countTokens()
        val arr = mutableListOf<String>()

        while (t.hasMoreTokens()) {
            arr.add(t.nextToken()!!)
        }
        return arr.toList()
    }

    fun toAscii(ba: ByteArray): String {
        val sb = StringBuilder()
        ba.forEach { b ->
            sb.append(Integer.toHexString(0xff and b.toInt()))
        }
        return sb.toString()
    }
}
