package femtioprocent.partytalk.extentions

fun <T> Collection<T>.formatSpecial(): String {
    return when (this.size) {
        0    -> "[]"
        1    -> this.first().toString()
        else -> "[${this.joinToString(",")}]"
    }
}

fun String.numerus(size: Int): String {
    if (size == 1)
        return this
    else
        return "${this}s"
}

fun String.toTTL(): Long {
    val regex = "(([0-9]*)[d])?(([0-9]*)h)?(([0-9]*)m)?(([0-9]*)s)?".toRegex()
    val m = regex.find(this) ?: return 0
    return m.groupValues.let { l ->
        try {
            val d = l[2].takeIf { it.isNotEmpty() }?.toInt() ?: 0
            val h = l[4].takeIf { it.isNotEmpty() }?.toInt() ?: 0
            val m = l[6].takeIf { it.isNotEmpty() }?.toInt() ?: 0
            val s = l[8].takeIf { it.isNotEmpty() }?.toInt() ?: 0

            (((d * 24 + h) * 60 + m) * 60 + s) * 1000L
        } catch (ex: NumberFormatException) {
            0
        }
    }
}