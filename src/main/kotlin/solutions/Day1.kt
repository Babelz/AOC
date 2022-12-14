package solutions

import Solution
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * Advent of code 2022 day 1 solution class.
 *
 * https://adventofcode.com/2022/day/1
 */
class Day1 : Solution {
    override fun run() {
        val lines = File("input/Day1").readLines()

        val result = listOf(0).plus(lines.indices.filter {
            lines[it].isNullOrEmpty()
        }).maxOfOrNull {
            lines.subList(it + 1, lines.size).takeWhile { l ->
                !l.isNullOrEmpty()
            }.sumOf { l -> l.toInt() }
        }

        println("Max calories: $result")
    }
}