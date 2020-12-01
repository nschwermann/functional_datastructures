package datastructures

import utils.*

class Graph<T>(private val directed : Boolean = false){

    private inner class Node<T>(val data : T){
        var visited : Boolean = false
        override fun equals(other: Any?): Boolean = other?.equals(data) == true
        override fun toString(): String = data.toString()
        override fun hashCode() : Int = data.hashCode()
    }

    private fun T.n() = Node(this)

    private val edges = Dictionary<Node<T>, HashSet<Node<T>>>()

    fun addEdge(sourceVertex: T, destinationVertex: T) {
        // Add edge to source vertex / node.
        edges[sourceVertex.n()].getOrElse(HashSet()).insert(destinationVertex.n()).also {
            edges[sourceVertex.n()] = it
        }
        if(!directed) edges[destinationVertex.n()].getOrElse(HashSet()).insert(sourceVertex.n()).also {
            edges[destinationVertex.n()] = it
        }
    }

    fun hasPath(start: T, end : T) : Boolean {
        resetMeta()
        val queue = Queue<Node<T>>()
        queue.add(start.n())
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

    fun findPath(start : T, end : T) : LinkedList<T> {
        resetMeta()
        if(start == end) LinkedList((LinkedList(start, end)))
        val que = Queue<Cons<Node<T>, LinkedList<T>>>()
        que.add(start.n() cons LinkedList.Empty)
        while(que.peek() !is Option.None){
            val next = que.remove().getOrThrow()
            if(next.car.visited) {
                continue
            }
            next.car.visited = true
            val path = next.cdr.append(next.car.data)
            if(next.car == end) return path
            edges[next.car].forEach {
                it.toTree().forEach { node ->
                    if(!node.visited){
                        que.add(node cons path)
                    }
                }
            }
        }
        return LinkedList.Empty
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