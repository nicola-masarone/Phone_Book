fun solution(numbers: List<Int>, number: Int): MutableList<Int> {
    val a = numbers.toMutableList()
    a.add(number)
    return a
}