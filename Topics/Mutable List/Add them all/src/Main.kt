fun solution(first: List<Int>, second: List<Int>): MutableList<Int> {
    // put your code here
    return first.toMutableList().apply { this.addAll(second) }
}