package phonebook

import java.io.File
import kotlin.math.*

fun hFunStrToInt(myString: String): Int {
    var hashVal= 0L
    val p = 13
    for (v in myString) {
        hashVal = (hashVal * p + v.toInt()) % Int.MAX_VALUE
    }
    return hashVal.toInt()
}

fun hFunIntToInt(myInt: Int): Int = myInt

fun hTableAdd(hTable: MutableList<MutableList<Pair<Int, Int>>>, item: Pair<String, Int>): MutableList<MutableList<Pair<Int, Int>>> {
    var resTable = hTable
    // Add item in the hash table using hash functions (first thing first we hash the String to Int)
    val hItem = Pair(hFunStrToInt(item.first), item.second)
    resTable[hFunIntToInt(hItem.first) % resTable.size].add(hItem)
    return resTable
}

fun quickSort(lines: List<String>): List<String> {
    if (lines.size < 2) {
        return lines
    }
    else {
        val pivot = lines.last()
        val leftSubList = mutableListOf<String>()
        val rightSubList = mutableListOf<String>()
        for (i in 0 until lines.lastIndex) {
            if (lines[i].substringAfter(" ") < pivot.substringAfter(" ")) {
                leftSubList.add(lines[i])
            } else {
                rightSubList.add(lines[i])
            }
        }
        return quickSort(leftSubList) + pivot + quickSort(rightSubList)
    }
}

fun bubbleSort(lines: List<String>, linMs: Long, startMs: Long): List<String> {
    var linesArray = lines.toTypedArray()
    var swap = false
    do {
        swap = false
        for (i in 0 until linesArray.lastIndex) {
            if (linesArray[i].substringAfter(" ") > linesArray[i + 1].substringAfter(" ")) {
                val dummy = linesArray[i + 1]
                linesArray[i + 1] = linesArray[i]
                linesArray[i] = dummy
                swap = true
            }
        }
        if ((System.currentTimeMillis() - startMs) > linMs * 10) {
            return lines
        }
    } while (swap)
    return linesArray.toList()
}

fun linSearch(lines: List<String>, target: String): Int {
    var pos = -1
    var linesArray = lines.toTypedArray()
    for (i in linesArray.indices) {
        if (target in linesArray[i]) {
            pos = i
            break
        }
    }
    return pos
}

fun jmpSearch(lines: List<String>, target: String): Int {
    var pos = -1
    var linesArray = lines.toTypedArray()
    val n = sqrt(linesArray.size.toDouble()).toInt()
    var blockL = 0
    var blockR = min(n - 1, linesArray.lastIndex)
    do {
        if (target <= linesArray[blockR].substringAfter(" ")) {
            // Block found
            for (i in blockR downTo blockL) {
                if (target == linesArray[i].substringAfter(" ")) {
                    // Target found
                    pos = i
                    return pos
                }
            }
            if (pos == -1) {
                // Target not found
                pos = -1
                return pos
            }
        } else if (blockR < linesArray.lastIndex) {
            // Try new block
            blockL = min(blockL + n, linesArray.lastIndex)
            blockR = min(blockR + n, linesArray.lastIndex)
        } else {
            // Target is not present
            pos = -1
            return pos
        }
    } while (true)

    return pos
}

fun binSearch(lines: List<String>, target: String, left: Int, right: Int): Int {
    val middle = (left + right) / 2
    if (target == lines[middle].substringAfter(" ")) {
        return middle
    } else {
        return when (right - left) {
            0 -> { -1 }
            else -> {
                if (target < lines[middle].substringAfter(" ")) {
                    // looking for target in the left subList
                    binSearch(lines, target, left, middle - 1)
                } else {
                    // looking for target in the right subList
                    binSearch(lines, target, middle + 1, right)
                }
            }
        }
    }
}

fun writeSortedDir(lines: List<String>) {
    val sortedDirFileName = "/home/nicola/Scaricati/sorted_directory.txt"
    val sortedDirFile = File(sortedDirFileName)
    for (line in lines) {
        sortedDirFile.appendText("$line\n")
    }
}

fun printMsToMinSecMs(ms: Long) {
    val minutes = ms / 1000 / 60
    val seconds = (ms / 1000) % 60
    print("$minutes min. $seconds sec. ${ms - minutes * 1000 * 60 - seconds * 1000} ms.")
}

fun main() {
    val findLines = File("/home/nicola/Scaricati/small_find.txt").readLines()
    val dirLines = File("/home/nicola/Scaricati/small_directory.txt").readLines()
    var count: Int
    var searchMs: Long
    var startTime: Long

    println("Start searching (linear search)...")
    startTime = System.currentTimeMillis()
    count = 0
    for (findline in findLines) {
        val targetPos = linSearch(dirLines, findline)
        if (targetPos != -1) { count++ }
    }
    searchMs = System.currentTimeMillis() - startTime
    print("Found $count / ${findLines.size} entries. Time taken: ")
    printMsToMinSecMs(searchMs)
    println()
    println()

    println("Start searching (bubble sort + jump search)...")
    startTime = System.currentTimeMillis()
    var sortedDirLines = bubbleSort(dirLines, searchMs, startTime)
    var sortMs = System.currentTimeMillis() - startTime
    if (sortedDirLines == dirLines) {
        startTime = System.currentTimeMillis()
        count = 0
        for (findline in findLines) {
            val targetPos = linSearch(dirLines, findline)
            if (targetPos != -1) { count++ }
        }
        searchMs = System.currentTimeMillis() - startTime
    } else {
        // just for test
        // writeSortedDir(sortedDirLines)
        startTime = System.currentTimeMillis()
        count = 0
        for (findline in findLines) {
            val targetPos = jmpSearch(sortedDirLines, findline)
            if (targetPos != -1) { count++ }
        }
        searchMs = System.currentTimeMillis() - startTime
    }
    print("Found $count / ${findLines.size} entries. Time taken: ")
    printMsToMinSecMs(sortMs + searchMs)
    println()
    print("Sorting time: ")
    printMsToMinSecMs(sortMs)
    if(sortedDirLines == dirLines) {
        println(" - STOPPED, moved to linear search")
    } else { println() }
    print("Searching time: ")
    printMsToMinSecMs(searchMs)
    println()
    println()

    println("Start searching (quick sort + binary search)...")
    startTime = System.currentTimeMillis()
    sortedDirLines = quickSort(dirLines)
    sortMs = System.currentTimeMillis() - startTime
    // just for test
    // writeSortedDir(sortedDirLines)
    startTime = System.currentTimeMillis()
    count = 0
    for (findline in findLines) {
        val targetPos = binSearch(sortedDirLines, findline, 0, sortedDirLines.lastIndex)
        if (targetPos != -1) { count++ }
    }
    searchMs = System.currentTimeMillis() - startTime
    print("Found $count / ${findLines.size} entries. Time taken: ")
    printMsToMinSecMs(sortMs + searchMs)
    println()
    print("Sorting time: ")
    printMsToMinSecMs(sortMs)
    println()
    print("Searching time: ")
    printMsToMinSecMs(searchMs)
    println()
    println()

    println("Start searching (hash table)...")
    val hashTableSize = ceil(dirLines.size.toDouble() / 0.75).toInt()
    var hTable = MutableList<MutableList<Pair<Int, Int>>>(hashTableSize) {it -> mutableListOf<Pair<Int, Int>>()}
    startTime = System.currentTimeMillis()
    for (dirLine in dirLines) {
        hTable = hTableAdd(hTable, Pair(dirLine.substringAfter(" "), dirLine.substringBefore(" ").toInt()))
    }
    sortMs = System.currentTimeMillis() - startTime
    startTime = System.currentTimeMillis()
    count = 0
    for (findline in findLines) {
        val hashedName = hFunStrToInt(findline)
        val bucket = hTable[hFunIntToInt(hashedName) % hTable.size]
        for (i in bucket) {
            if (i.first == hashedName) {
                count++
            }
        }
    }
    searchMs = System.currentTimeMillis() - startTime
    print("Found $count / ${findLines.size} entries. Time taken: ")
    printMsToMinSecMs(sortMs + searchMs)
    println()
    print("Creating time: ")
    printMsToMinSecMs(sortMs)
    println()
    print("Searching time: ")
    printMsToMinSecMs(searchMs)
    println()

    print("Enter name to find phone number (please leave one space between name and surname): ")
    val name = readLine()!!
    val hashedName = hFunStrToInt(name)
    val bucket = hTable[hFunIntToInt(hashedName) % hTable.size]
    for (i in bucket) {
        if (i.first == hashedName) {
            println("$name - phone number : ${i.second}")
        }
    }
}
