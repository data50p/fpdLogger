package femtioprocent.sundry

import java.util.*

object Sundry {
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
}
