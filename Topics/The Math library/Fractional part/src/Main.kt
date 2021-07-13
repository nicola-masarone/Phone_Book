import kotlin.math.truncate

fun main() {
    // put your code here
    val num = readLine()!!.toDouble()
    println(truncate((num - truncate(num)) * 10).toInt())
}