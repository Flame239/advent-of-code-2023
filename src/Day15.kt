fun main() {
    val input = readInput("Day15")[0].split(",")

    fun hash(s: String): Int = s.fold(0) { acc, c -> (acc + c.code) * 17 % 256 }

    fun part1(): Int = input.sumOf { hash(it) }

    fun part2(): Int {
        data class Lens(val label: String, var focus: Int)

        val boxes = Array(256) { ArrayList<Lens>() }

        input.forEach { s ->
            if (s.contains('-')) {
                val label = s.substringBefore("-")
                boxes[hash(label)].removeIf { (l, _) -> l == label }
            } else {
                val lens = s.split("=").let { Lens(it[0], it[1].toInt()) }
                val curBox = boxes[hash(lens.label)]
                val existingLens = curBox.find { l -> l.label == lens.label }
                if (existingLens != null) existingLens.focus = lens.focus else curBox.add(lens)
            }
        }

        return boxes.withIndex().sumOf { (i, lens) -> lens.withIndex().sumOf { (k, len) -> (i + 1) * (k + 1) * len.focus } }
    }

    measure { part1() }
    measure { part2() }
}
