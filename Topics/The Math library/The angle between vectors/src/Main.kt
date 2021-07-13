import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val (u1, u2) = readLine()!!.split(" ").map { it.toDouble() }
    val (v1, v2) = readLine()!!.split(" ").map { it.toDouble() }

    val angle = acos((u1 * v1 + u2 * v2) /
            (sqrt(u1.pow(2) + u2.pow(2)) * sqrt(v1.pow(2) + v2.pow(2)))
    )
    println((angle / PI * 180).toInt())
}