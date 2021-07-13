import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val a = readLine()!!.toDouble()
    val b = readLine()!!.toDouble()
    val c = readLine()!!.toDouble()
    val x1 = (-b - sqrt(b.pow(2.0) - 4.0 * a * c)) / (2.0 * a)
    val x2 = (-b + sqrt(b.pow(2.0) - 4.0 * a * c)) / (2.0 * a)
    if (x1 < x2) {
        print("$x1 $x2")
    } else {
        print("$x2 $x1")
    }
}