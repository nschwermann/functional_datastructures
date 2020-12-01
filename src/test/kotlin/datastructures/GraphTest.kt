package datastructures

import org.junit.Test

import org.junit.Assert.*

class GraphTest {

    @Test
    fun directedEdgeString(){
        val result = Graph<Int>(directed = true).apply {
            addEdge(0, 1)
            addEdge(1, 2)
            addEdge(2, 3)
            addEdge(3, 1)
        }.toString().trimMargin()
        val expected = """
            0→[1]
            1→[2]
            2→[3]
            3→[1]
        """.trimIndent()
        assertEquals(expected, result)
    }

    @Test
    fun undirectedEdgeString(){
        val result = Graph<Int>(directed = false).apply {
            addEdge(0, 1)
            addEdge(1, 2)
            addEdge(2, 3)
            addEdge(3, 1)
        }.toString().trimMargin()
        val expected = """
            0→[1]
            1→[0, 2, 3]
            2→[1, 3]
            3→[2, 1]
        """.trimIndent()
        assertEquals(expected, result)
    }

    @Test
    fun hasPathDirected() {
        val graph = Graph<Int>(directed = true).apply {
            addEdge(0, 1)
            addEdge(0, 4)
            addEdge(0, 5)
            addEdge(1, 4)
            addEdge(1, 3)
            addEdge(2, 1)
            addEdge(3, 2)
            addEdge(3, 4)
            addEdge(6, 6)
        }
        assertTrue(graph.hasPath(0, 3))
        assertFalse(graph.hasPath(5, 2))
        assertFalse(graph.hasPath(4, 3))
        assertFalse(graph.hasPath(6, 5))
        assertTrue(graph.hasPath(6, 6))
    }

    @Test
    fun hasPathUndirected() {
        val graph = Graph<Int>(directed = false).apply {
            addEdge(0, 1)
            addEdge(0, 4)
            addEdge(0, 5)
            addEdge(1, 4)
            addEdge(1, 3)
            addEdge(2, 1)
            addEdge(3, 2)
            addEdge(3, 4)
            addEdge(6, 6)
        }
        assertTrue(graph.hasPath(0, 3))
        assertTrue(graph.hasPath(5, 2))
        assertTrue(graph.hasPath(4, 3))
        assertFalse(graph.hasPath(6, 5))
        assertTrue(graph.hasPath(6, 6))
    }

    @Test
    fun findPathsDirected(){
        val graph = Graph<Int>(directed = true).apply {
            addEdge(0, 1)
            addEdge(0, 4)
            addEdge(0, 5)
            addEdge(1, 4)
            addEdge(1, 3)
            addEdge(2, 1)
            addEdge(3, 2)
            addEdge(3, 4)
            addEdge(6, 6)
        }

        assertEquals(LinkedList(0,1,3,2), graph.findPath(0,2))
        assertEquals(LinkedList(0,5), graph.findPath(0,5))
        assertEquals(LinkedList(0,4), graph.findPath(0,4))
        assertEquals(LinkedList(0,1,3), graph.findPath(0,3))
        assertEquals(LinkedList.Empty, graph.findPath(4,3))
    }

    @Test
    fun findPathsUndirected(){
        val graph = Graph<Int>(directed = false).apply {
            addEdge(0, 1)
            addEdge(0, 4)
            addEdge(0, 5)
            addEdge(1, 4)
            addEdge(1, 3)
            addEdge(2, 1)
            addEdge(3, 2)
            addEdge(3, 4)
            addEdge(6, 6)
        }

        assertEquals(LinkedList(0,1,2), graph.findPath(0,2))
        assertEquals(LinkedList(0,5), graph.findPath(0,5))
        assertEquals(LinkedList(0,4), graph.findPath(0,4))
        assertEquals(LinkedList(0,1,3), graph.findPath(0,3))
        assertEquals(LinkedList(4,3), graph.findPath(4,3))
        assertEquals(LinkedList(5,0,1,2), graph.findPath(5,2))
        assertEquals(LinkedList(5,0,1,3), graph.findPath(5,3))
    }

}