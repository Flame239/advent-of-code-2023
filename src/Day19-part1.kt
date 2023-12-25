fun main() {
    data class Part(val x: Int, val m: Int, val a: Int, val s: Int) {
        val rating: Int = x + m + a + s
    }

    data class Rule(val apply: (Part) -> String?)
    data class Workflow(val name: String, val rules: List<Rule>) {
        fun process(p: Part): String = rules.map { r -> r.apply(p) }.first { it != null }!!
    }

    fun extract(prop: String, p: Part): Int {
        return when (prop) {
            "x" -> p.x
            "m" -> p.m
            "a" -> p.a
            "s" -> p.s
            else -> error("No prop")
        }
    }

    val input = readInput("Day19").splitBy { it.isEmpty() }
    val parts: List<Part> = input[1].map { p -> p.substring(1, p.length - 1).split(',').map { n -> n.split("=")[1].toInt() }.let { Part(it[0], it[1], it[2], it[3]) } }
    val workflows: Map<String, Workflow> = input[0].map { w ->
        val name = w.substringBefore("{")
        val rules = w.substringAfter("{").substringBefore("}").split(',').map { r ->
            var condition: (Part) -> Boolean = { _: Part -> true }
            val dst: String
            if (r.contains('>')) {
                condition = r.substringBefore(':').split('>').let { { p -> extract(it[0], p) > it[1].toInt() } }
                dst = r.substringAfter(':')
            } else if (r.contains('<')) {
                condition = r.substringBefore(':').split('<').let { { p -> extract(it[0], p) < it[1].toInt() } }
                dst = r.substringAfter(':')
            } else {
                dst = r
            }
            Rule { part -> if (condition(part)) dst else null }
        }
        Workflow(name, rules)
    }.associateBy(Workflow::name)

    fun part1(): Int {
        return parts.filter { p ->
            var cur = "in"
            while (cur != "A" && cur != "R") {
                cur = workflows[cur]!!.process(p)
            }
            cur == "A"
        }.sumOf(Part::rating)
    }

    measure { part1() }
}
