package solutions

import Solution
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * Advent of code 2022 day 1 solution.
 *
 * https://adventofcode.com/2022/day/1
 */
class Day1 : Solution {
    override fun run() {
        val lines = File("input/Day1").readLines()

        val result = listOf(-1).plus(lines.indices.filter {
            lines[it].isNullOrEmpty()
        }).map { it + 1 }.dropLast(1).map {
            lines.subList(it, lines.size).takeWhile { l ->
                !l.isNullOrEmpty()
            }.sumOf { l ->
                l.toInt()
            }
        }

        println("Max calories: ${result.max()}")
        println("Total calories of top 3 elves: ${result.sortedDescending().subList(0, 3).sum()}")
    }
}