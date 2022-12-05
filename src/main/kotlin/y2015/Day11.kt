package y2015

fun main(args: Array<String>) {

    "hxbxwxba".getNext().also(::println)
    "hxbxwxba".getNext().getNext().also(::println)
//    "hijklmmn".also { check(it.check1()) }.also { check(!it.check2()) }
//    "abbceffg".also { check(!it.check1()) }.also { check(it.check3()) }
//    "abbcegjk".also { check(!it.check3()) }
//    "abcdefgh".let { it.getNext() }.also(::println).also { check(it == "abcdffaa")}
//    "ghijklmn".also { check(it.getNext() == "ghjaabcc") }

}

private tailrec fun String.getNext(): String {
    var carryOver = 1
    val nextWord = reversed().mapIndexed { index, c ->
        var next = (c + carryOver)
        carryOver = 0
        if (next > 'z') {
            next = 'a'
            carryOver = 1
        }
        next
    }.joinToString("").reversed()
//    println("Next word = $nextWord")
    return if (nextWord.check1() && nextWord.check2() && nextWord.check3()) {
        nextWord
    } else {
        nextWord.getNext()
    }
}

private fun String.check1(): Boolean {
    val windowed = windowed(3, 1)
    return windowed.map {
        it[2] == it[1] + 1 && it[1] == it[0] + 1
    }.any { it }
}

private fun String.check2(): Boolean {
    return !(contains('i') || contains('o')|| contains('l'))
}
private fun String.check3(): Boolean {
    val repeatingCount = Regex("(.)\\1").findAll(this).count()
    return repeatingCount >= 2
}

