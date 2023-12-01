package y2020

import isHex
import isNumber
import measure
import readInput
import splitBy

fun main() {
    data class Pass(val props: Map<String, String>) {
        val eyeColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        val mandatoryProps = listOf(
            "byr" to { s: String -> s.isNumber() && (1920..2002).contains(s.toInt()) },
            "iyr" to { s -> s.isNumber() && (2010..2020).contains(s.toInt()) },
            "eyr" to { s -> s.isNumber() && (2020..2030).contains(s.toInt()) },
            "hgt" to { s ->
                val metric = s.takeLast(2)
                val value = s.dropLast(2)
                when (metric) {
                    "in" -> value.isNumber() && (59..76).contains(value.toInt())
                    "cm" -> value.isNumber() && (150..193).contains(value.toInt())
                    else -> false
                }
            },
            "hcl" to { s -> s.startsWith("#") && s.length == 7 && s.drop(1).all { it.isHex() } },
            "ecl" to { s -> eyeColors.contains(s) },
            "pid" to { s -> s.isNumber() && s.length == 9 }
        )

        fun isValid1(): Boolean = mandatoryProps.all { props.containsKey(it.first) }
        fun isValid2(): Boolean =
            mandatoryProps.all { (name, validator) -> props.containsKey(name) && validator(props[name]!!) }
    }

    val passports by lazy {
        readInput("2022-4")
            .splitBy { it.isEmpty() }
            .map { passLines -> passLines.flatMap { it.split(" ") } }
            .map { props -> props.associate { prop -> prop.split(":").let { Pair(it[0], it[1]) } } }
            .map { Pass(it) }
    }

    fun part1(): Int = passports.count { it.isValid1() }

    fun part2(): Int = passports.count { it.isValid2() }

    measure { part1() }
    measure { part2() }
}
