import java.lang.Long.min

fun main() {
    data class Range(val start: Long, val end: Long)
    data class RangeMap(val dstStart: Long, val start: Long, val length: Long) {
        val end = start + length - 1
        val shift = dstStart - start
        fun contains(src: Long) = src in start..end
    }

    class RangeMapper(initRangeMaps: List<RangeMap>) {
        private val rangeMaps = initRangeMaps.sortedBy { it.start }

        fun map(src: Long): Long = rangeMaps.firstOrNull { it.contains(src) }?.let { src + it.shift } ?: src

        fun mapRange(range: Range): List<Range> {
            if (range.end < rangeMaps[0].start || range.start > rangeMaps.last().end) return listOf(range)
            return mapRangePartial(rangeMaps.indexOfFirst { it.end >= range.start }, range)
        }

        fun mapRangePartial(i: Int, range: Range): List<Range> {
            if (i >= rangeMaps.size || range.end < rangeMaps[i].start || range.start > rangeMaps.last().end) return listOf(range)
            val newRanges = mutableListOf<Range>()
            val curMap = rangeMaps[i]

            if (curMap.start > range.start) {
                newRanges.add(Range(range.start, curMap.start - 1))
                newRanges.add(Range(curMap.start + curMap.shift, min(range.end, curMap.end) + curMap.shift))
            } else {
                newRanges.add(Range(range.start + curMap.shift, min(range.end, curMap.end) + curMap.shift))
            }

            if (range.end > curMap.end) {
                newRanges.addAll(mapRangePartial(i + 1, Range(curMap.end + 1, range.end)))
            }
            return newRanges
        }
    }

    data class SeedMapping(val seeds: List<Long>, val mappers: List<RangeMapper>)

    val mappings by lazy {
        val inputBlocks = readInput("Day5").splitBy { it.isBlank() }
        val seeds = inputBlocks[0][0].substringAfter(": ").split(" ").map(String::toLong)
        val mappers = inputBlocks.drop(1).map { lines ->
            lines.drop(1).map { r -> r.split(" ").map(String::toLong).let { RangeMap(it[0], it[1], it[2]) } }
        }.map(::RangeMapper)
        SeedMapping(seeds, mappers)
    }

    fun part1(): Long = mappings.seeds.minOf { seed ->
        mappings.mappers.fold(seed) { acc, rangeMapper -> rangeMapper.map(acc) }
    }

    fun part2(): Long {
        var ranges = mappings.seeds.chunked(2).map { Range(it[0], it[0] + it[1] - 1) }
        mappings.mappers.forEach { m -> ranges = ranges.flatMap { m.mapRange(it) } }
        return ranges.minOf { it.start }
    }

    measure { part1() }
    measure { part2() }
}
