import kotlin.math.cos
import kotlin.math.sin

fun main() {
    val angle = readLine()!!.toDouble()
    println(sin(angle) - cos(angle))
}