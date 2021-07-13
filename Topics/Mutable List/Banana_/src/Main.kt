fun solution(strings: MutableList<String>, str: String): MutableList<String> {
    strings.replaceAll { it -> if (it == str) "Banana" else it }
    return strings
}