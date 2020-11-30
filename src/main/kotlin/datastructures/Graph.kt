package datastructures

import utils.*

interface NodeMeta{
    var visited : Boolean
}

data class Num(val int: Int, override var visited: Boolean = false) : NodeMeta{
    override fun equals(other: Any?): Boolean {
        return if(other is Num) int == other.int
        else false
    }

    override fun hashCode(): Int {
        return int
    }

    override fun toString(): String {
        return int.toString(10)
    }
}
fun Int.m() : Num = Num(this, false)

class Graph<T : NodeMeta>(private val directed : Boolean = false){

    private val edges = Dictionary<T, HashSet<T>>()

    fun addEdge(sourceVertex: T, destinationVertex: T) {
        // Add edge to source vertex / node.
        edges[sourceVertex].getOrElse(HashSet()).insert(destinationVertex).also {
            edges[sourceVertex] = it
        }
        if(!directed) edges[destinationVertex].getOrElse(HashSet()).insert(sourceVertex).also {
            edges[destinationVertex] = it
        }
    }

    fun hasPath(start: T, end : T) : Boolean{
        resetMeta()
        val queue = Queue<T>()
        queue.add(start)
        while(queue.peek() !is Option.None){
            val vert = queue.remove().getOrThrow()
            if(vert == end) return true
            if(vert.visited ) continue
            vert.visited = true
            edges[vert].forEach {
                it.toTree().forEach { node -> queue.add(node) }
            }
        }
        return false
    }

    override fun toString(): String {
        return buildString {
            edges.toTree().forEach {
                append(it.car)
                append('→')
                append(it.cdr.toString())
                append('\n')
            }
        }
    }

    private fun resetMeta(){
        edges.toTree().forEach { (key, value) ->
            key.visited = false
            value.toTree().forEach { it.visited = false }
        }
    }
}