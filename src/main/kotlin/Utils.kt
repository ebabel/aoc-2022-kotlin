import kotlin.math.pow
import kotlin.math.sign

fun main() {
//    (1..5).toList()
//        .permutations(true)
//        .also { println(it.size) }
//        .forEach {
//            println(it.joinToString(","))
//        }
}

fun factorial(num: Int) = (1..num).reduce(Int::times)
val Int.factorial: Int
    get() = factorial(this)

fun <R> List<R>.permutations(preventDupes: Boolean = false): List<List<R>> {
//    val permutationTable = if (preventDupes) {
//        MutableList<List<R>?>(size.factorial) {null}
//    } else {
//        MutableList<List<R>>(size.toDouble().pow(size).toInt()) { emptyList() }
//    } TODO does preloading size make this faster? use arrays?
    val permutationTable = mutableListOf<List<R>>()
    permutations(size, indices, preventDupes) { index ->
        permutationTable.add(
            index.map { this[it] }//.also{ println(permutationTable.size) }
        )
    }
    return permutationTable.toList()
}

fun permutations(items: Int, operation: (List<Int>) -> Unit) {
    permutations(items, 1..items, true) { iteration ->
        operation(iteration)
    }
}

fun <R> permutationLists(items: Int, operation: (List<Int>) -> List<R>?): List<List<R>> {
    val permutationTable = mutableListOf<List<R>>()
    permutations(items, 1..items, true) { iteration ->
        operation(iteration)?.let { permutationTable.add(it) }
    }
    return permutationTable.toList()
}

/**
 *  Pass in number of boxes, and index range per box and operation will be called per permutation of indices.
 *
 * If prevent dupes is true, operation will be called itemSpaces.pow(itemRange) times. Otherwise x! times.
 * Very slow for more than 5-7^(5-7) times, even with prevent dupes set to true.
 */

fun permutations(itemSpaces: Int, itemRange: IntRange, preventDupes: Boolean = false, operation: (List<Int>) -> Unit) {
    val mutableList = MutableList(itemSpaces) { itemRange.first }
    fun increment(workingIndex: Int): Boolean {
        if (workingIndex == itemSpaces) {
            return true
        }
        mutableList[workingIndex]++
        if (mutableList[workingIndex] > itemRange.last) {
            mutableList[workingIndex] = 0
            return increment(workingIndex + 1)
        }
        return false
    }
    while (true) {
        if (!preventDupes || (mutableList.toSet().size == mutableList.size))
            operation(mutableList)
        val isDone = increment(0)
        if (isDone) {
            break
        }
    }

}

fun List<Any>.alsoPrintOnLines() {
    printOnLn(this)
}
fun printOnLn(list: List<Any>) {
    list.forEach { println(it) }
}