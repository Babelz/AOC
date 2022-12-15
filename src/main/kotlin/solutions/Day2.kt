package solutions

import Solution
import java.io.File
import java.lang.Exception

enum class Hand(val symbols: Array<Char>, val score: Int) {
    // Rock, paper, scissors.
    Rock(arrayOf('A', 'X'), 1),
    Paper(arrayOf('B', 'Y'), 2),
    Scissors(arrayOf('C', 'Z'), 3);
}

enum class Outcome(val score: Int) {
    Lose(0),
    Draw(3),
    Win(6);
}

/**
 * Advent of code 2022 day 2 solution.
 *
 * https://adventofcode.com/2022/day/1
 */
class Day2 : Solution {
    private data class Result(val outcome: Outcome, val hand: Hand)

    override fun run() {
        fun solve(rules: Map<Hand, (hands: Pair<Hand, Hand>) -> Result>): Int {
            return File("input/Day2").readLines().filter { it.isNotEmpty() }.map {
                it.replace(" ", "").toCharArray().takeIf { c -> c.isNotEmpty() } ?:
                throw Exception("line contains no tokens")
            }.map {
                val (a, b) = it.map { c ->
                    Hand.values().first() { v -> v.symbols.contains(c) }
                }

                rules[b]!!(Pair(a, b))
            }.sumOf {
                it.hand.score + it.outcome.score
            }
        }

        println("Normal score: ${solve(mapOf(
            Hand.Rock to {
                mapOf(Hand.Paper to Result(Outcome.Lose, it.second), 
                      Hand.Scissors to Result(Outcome.Win, it.second))[it.first] ?: Result(Outcome.Draw, it.second)
            },
            Hand.Paper to {
                mapOf(Hand.Rock to Result(Outcome.Win, it.second), 
                      Hand.Scissors to Result(Outcome.Lose, it.second))[it.first] ?: Result(Outcome.Draw, it.second)
            },
            Hand.Scissors to {
                mapOf(Hand.Rock to Result(Outcome.Lose, it.second), 
                      Hand.Paper to Result(Outcome.Win, it.second))[it.first] ?: Result(Outcome.Draw, it.second)
            }
        ))}")

        println("Strange score: ${solve(mapOf(
            // Rock always loses.
            Hand.Rock to {
                Result(Outcome.Lose, when (it.first) {
                    Hand.Rock     -> Hand.Scissors
                    Hand.Paper    -> Hand.Rock
                    Hand.Scissors -> Hand.Paper
                })
            },
            // Paper always draws.
            Hand.Paper to {
                Result(Outcome.Draw, it.first)
            },
            // Scissors always win.
            Hand.Scissors to {
                Result(Outcome.Win, when (it.first) {
                    Hand.Rock     -> Hand.Paper
                    Hand.Paper    -> Hand.Scissors
                    Hand.Scissors -> Hand.Rock
                })
            }
        ))}")
    }
}