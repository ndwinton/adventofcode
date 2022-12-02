package aoc2022


fun maximumCalories(input: String): Int = caloriesList(input).max()

private fun caloriesList(input: String) =
    input.split(Regex("""\n\n"""))
        .map {
            it.split("\n").map { it.toInt() }.sum()
        }

fun topThreeCaloriesTotal(input: String): Int = caloriesList(input).sortedDescending().take(3).sum()
