package femtioprocent.partytalk.extentions

import femtioprocent.sundry.Ansi

class Extentions

/**
 * Better than:
 * 	    RetainedDatagram.retainedDatagramMap.entries.groupBy({ it.value.`when` < now }, { it.key })
 *          -> [true] resp [false]
 * ?
 */
inline fun <K, V> Map<K, V>.partition(predicate: (Map.Entry<K, V>) -> Boolean): Pair<Map<K, V>, Map<K, V>> {
    val map1 = mutableMapOf<K, V>()
    val map2 = mutableMapOf<K, V>()
    entries.forEach {
        if (predicate(it)) {
            map1[it.key] = it.value
        } else {
            map2[it.key] = it.value
        }
    }
    return Pair(map1, map2)
}

inline fun <K, V, T> Map<K, V>.partition(predicate: (Map.Entry<K, V>) -> Boolean, transform: (Map.Entry<K, V>) -> T): Pair<Map<K, T>, Map<K, T>> {
    val map1 = mutableMapOf<K, T>()
    val map2 = mutableMapOf<K, T>()
    entries.forEach {
        if (predicate(it)) {
            map1[it.key] = transform(it)
        } else {
            map2[it.key] = transform(it)
        }
    }
    return Pair(map1, map2)
}


/**
 * Use standard groupBy instead
 */
fun <TK, TV> MutableMap<TK, MutableList<TV>>.addIntoListForSure(key: TK, value: TV): MutableMap<TK, MutableList<TV>> {
    if (key in this)
        this[key]?.add(value)
    else
        this[key] = mutableListOf(value)
    return this
}


fun Boolean.ansiColor(): String {
    return if (this) Ansi.fg(Ansi.LegacyColor.G, "T") else Ansi.fg(Ansi.LegacyColor.R, "f")
}

fun String.ansiColor(cc: Ansi.LegacyColor): String {
    return Ansi.fg(cc, this)
}

fun String.ansiColor(cc: Ansi.Color5, value: Int): String {
    return Ansi.color5Bg(cc, value, this)
}

fun main() {
    val map = mapOf(
        1 to "a",
        2 to "b",
        3 to "c"
    )
    val set = setOf(1, 3)

    val (m1, m2) = map
        .asIterable()
        .partition { (key, _) -> key in set }
        .let { (l1, l2) -> l1.toMap() to l2.toMap() }

    println(m1)
    println(m2)
}

fun <K, V> Iterable<Map.Entry<K, V>>.toMap() = associate { e -> e.key to e.value }
