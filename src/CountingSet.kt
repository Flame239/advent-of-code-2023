class CountingSet<T> : Iterable<T> {
    private val map: HashMap<T, Int>

    constructor(map: Map<T, Int>) {
        this.map = HashMap(map)
    }

    constructor() : this(HashMap())

    fun get(key: T): Int = map.getOrDefault(key, 0)

    fun inc(key: T) {
        map[key] = map.getOrDefault(key, 0) + 1
    }

    fun dec(key: T) {
        val cur: Int = map[key] ?: throw IllegalStateException("Could not decrement below 0")
        if (cur == 1) {
            map.remove(key)
        } else {
            map[key] = cur - 1
        }
    }

    fun remove(key: T) = map.remove(key)

    fun contains(key: T): Boolean = map.containsKey(key)

    override fun iterator() = map.keys.iterator()
}
