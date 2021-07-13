fun square(value: Int, context: Any, continuation: (Int, Any) -> Unit) {
    // TODO: provide implementation here
    val result = value * value
    continuation(result, context)
}