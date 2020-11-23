package datastructures

class HashSet<T> {

    private val lookup = Dictionary<Int, T>()

    companion object{
        operator fun <T>invoke(collection: Collection<T>) : HashSet<T> {
            return HashSet<T>().apply{
                collection.forEach {
                    lookup[it.hashCode()] = it
                }
            }
        }

        operator fun <T>invoke(vararg args: T) : HashSet<T>{
            return HashSet<T>().apply {
                args.forEach { insert(it) }
            }
        }
    }

    fun insert(data : T) : HashSet<T> = apply {
        lookup[data.hashCode()] = data
    }

    operator fun contains(value : T) : Boolean{
        return value.hashCode() in lookup
    }

    fun remove(data : T){
        lookup.delete(data.hashCode())
    }

    override fun toString(): String {
        return buildString {
            append('[')
            lookup.toTree().fold(BinTree.Order.PreOrder, Unit){next, _ -> append(next.cdr).append(',').append(' ')}
            deleteRange(length - 2, length)
            append(']')
        }
    }

    fun toTree() : BinTree<T> = lookup.toTree().map { it.cdr }
}