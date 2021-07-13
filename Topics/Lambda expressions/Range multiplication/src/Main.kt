val lambda = { a: Long, b: Long ->
        var result: Long = 1
        for (i in a..b) {
            result *= i
        }
        result
}
