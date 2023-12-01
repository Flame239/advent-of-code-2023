package y2020

import measure
import readInput

private data class BagChild(val bag: Bag, val count: Int)
private data class Bag(val id: String, val parents: ArrayList<Bag>, val children: ArrayList<BagChild>) {
    fun addChild(child: Bag, count: Int) {
        this.children.add(BagChild(child, count))
        child.parents.add(this)
    }
}

fun main() {
    val goldBag by lazy {
        val bags = hashMapOf<String, Bag>()
        val prefixBagRegex = Regex("^([a-z]+ [a-z]+) bags contain (\\d+) ([a-z]+ [a-z]+) bags?")
        val suffixBagRegex = Regex(", (\\d+) ([a-z]+ [a-z]+) bags?")
        val emptyBagRegexp = Regex("([a-z]+ [a-z]+) bags contain no other bags\\.")

        readInput("2020-7").forEach {
            if (it.contains("contain no other bags")) {
                val id = emptyBagRegexp.find(it)!!.groupValues[1]
                bags[id] = Bag(id, arrayListOf(), arrayListOf())
                return@forEach
            }

            val (parentId, count, childId) = prefixBagRegex.find(it)!!.groupValues.drop(1)

            val parent = bags.computeIfAbsent(parentId) { _ -> Bag(parentId, arrayListOf(), arrayListOf()) }
            val child = bags.computeIfAbsent(childId) { _ -> Bag(childId, arrayListOf(), arrayListOf()) }
            parent.addChild(child, count.toInt())

            suffixBagRegex.findAll(it).map { m -> m.groupValues.drop(1) }.forEach { (curCount, curChildId) ->
                val curChild = bags.computeIfAbsent(curChildId) { _ -> Bag(curChildId, arrayListOf(), arrayListOf()) }
                parent.addChild(curChild, curCount.toInt())
            }
        }
        bags["shiny gold"]!!
    }

    fun part1(): Int {
        val allParents = hashSetOf<String>()
        var parents: List<Bag> = goldBag.parents

        while (parents.isNotEmpty()) {
            allParents.addAll(parents.map { it.id })
            parents = parents.flatMap { p -> p.parents }
        }

        return allParents.size
    }

    fun getChildrenSize(bag: Bag): Long = bag.children.sumOf { (getChildrenSize(it.bag) + 1) * it.count }

    fun part2(): Long = getChildrenSize(goldBag)


    measure { part1() }
    measure { part2() }
}
