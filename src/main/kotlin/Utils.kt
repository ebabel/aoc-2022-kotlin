fun main() {
//    (1..5).toList()
//        .permutations(true)
//        .also { println(it.size) }
//        .forEach {
//            println(it.joinToString(","))
//        }

    permutationSet(20).forEach {
        println(it)
    }
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
 * with prevent dupes = true:
 * 1,2,3
 * 1,3,2
 * 2,1,3
 * 2,3,1
 * 3,1,2
 * 3,2,1
 *
 * with prevent dupes = false:
 * 1,1,1
 * 1,1,2
 * 1,1,3
 * 1,2,1
 * etc.
 *
 *  Pass in number of boxes, and index range per box and operation will be called per permutation of indices.
 *
 * Prevent dupes means: should allow the same item could show up in two different spaces.
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
            mutableList[workingIndex] = itemRange.first
            return increment(workingIndex + 1)
        }
        return false
    }
    while (true) {
        if (!preventDupes || (mutableList.toSet().size == mutableList.size)) {
            operation(mutableList)
        }
        val isDone = increment(0)
        if (isDone) {
            break
        }
    }

}

/**
[1, 2, 3]
[1, 2]
[1, 3]
[2, 3]
[1]
[2]
[3]
 */
fun permutationSet(items: Int, operation: (List<Int>) -> Boolean): List<List<Int>> {

    val listListItems =
        (1..items).toList().map { listOf(it) }

    return (1..items).fold(listListItems) { acc, i ->
        acc.flatMap { a ->
            (1..items).mapNotNull { b->
//                println("  $i $b $a")
                if (!a.contains(b) && operation(a.plus(b))) a.plus(b).sorted() else null
            }.distinct()
        }.plus(listListItems)
    }

}
fun permutationSet(items: Int): List<List<Int>> {

    val listListItems =
        (1..items).toList().map { listOf(it) }

    return (2..items).fold(listListItems) { acc, i ->
        acc.flatMap { a ->
            (1..items).mapNotNull { b->
//                println("  $i $b $a")
                if ( !a.contains(b) && a.first() > b) a.plus(b).sorted() else null
            }.distinct()
        }.plus(listListItems)
    }

}
/**
[1, 2, 3]
[1, 3, 2]
[2, 1, 3]
[2, 3, 1]
[3, 1, 2]
[3, 2, 1]
[1, 2]
[1, 3]
[2, 1]
[2, 3]
[3, 1]
[3, 2]
[1]
[2]
[3]
 */
fun permutationSet2(items: Int): List<List<Int>> {

    val listListItems =
        (1..items).toList().map { listOf(it) }

    return (2..items).fold(listListItems) { acc, i ->
        acc.flatMap { a ->
            (1..items).mapNotNull { b->
                if (!a.contains(b)) a.plus(b) else null
            }
        }.plus(listListItems)
    }

}

fun <R> List<R>.alsoPrintOnLines(): List<R> {
    printOnLn(this)
    return this
}

fun <R> printOnLn(list: List<R>) {
    list.forEach { println(it) }
}