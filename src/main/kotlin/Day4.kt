import java.math.BigInteger
import java.security.MessageDigest

fun main(args: Array<String>) {

    val hasher = MessageDigest.getInstance("md5")
    var count = 0
    while (true) {
        count++
        val message = md5("ckczppom$count")
        if (message.startsWith("000000")) {
            println(count)
            break
        }
    }
}
private val md = MessageDigest.getInstance("MD5")

fun md5(input:String): String {
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

fun String.decodeHex(): ByteArray {
    check(length % 2 == 0) { "Must have an even length" }

    return chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()
}

private val input = """
ckczppom
""".trimIndent()