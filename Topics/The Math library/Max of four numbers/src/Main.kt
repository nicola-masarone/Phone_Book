fun main() {
    val nums = IntArray(4)
    var maxVal = 0
    for (i in nums.indices) {
        nums[i] = readLine()!!.toInt()
        if (i == 0) {
            maxVal = nums[i]
        }
        if (nums[i] > maxVal) {
            maxVal = nums[i]
        }
    }
    println(maxVal)
}