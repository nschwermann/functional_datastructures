package datastructures

import org.junit.Test

import org.junit.Assert.*

class GraphTest {

    @Test
    fun directedEdgeString(){
        val result = Graph<Num>(directed = true).apply {
            addEdge(0.m(), 1.m())
            addEdge(1.m(), 2.m())
            addEdge(2.m(), 3.m())
            addEdge(3.m(), 1.m())
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
        val result = Graph<Num>(directed = false).apply {
            addEdge(0.m(), 1.m())
            addEdge(1.m(), 2.m())
            addEdge(2.m(), 3.m())
            addEdge(3.m(), 1.m())
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
        val graph = Graph<Num>(directed = true).apply {
            addEdge(0.m(), 1.m())
            addEdge(0.m(), 4.m())
            addEdge(0.m(), 5.m())
            addEdge(1.m(), 4.m())
            addEdge(1.m(), 3.m())
            addEdge(2.m(), 1.m())
            addEdge(3.m(), 2.m())
            addEdge(3.m(), 4.m())
            addEdge(6.m(), 6.m())
        }
        assertTrue(graph.hasPath(0.m(), 3.m()))
        assertFalse(graph.hasPath(5.m(), 2.m()))
        assertFalse(graph.hasPath(4.m(), 3.m()))
        assertFalse(graph.hasPath(6.m(), 5.m()))
        assertTrue(graph.hasPath(6.m(), 6.m()))
    }

    @Test
    fun hasPathUndirected() {
        val graph = Graph<Num>(directed = false).apply {
            addEdge(0.m(), 1.m())
            addEdge(0.m(), 4.m())
            addEdge(0.m(), 5.m())
            addEdge(1.m(), 4.m())
            addEdge(1.m(), 3.m())
            addEdge(2.m(), 1.m())
            addEdge(3.m(), 2.m())
            addEdge(3.m(), 4.m())
            addEdge(6.m(), 6.m())
        }
        assertTrue(graph.hasPath(0.m(), 3.m()))
        assertTrue(graph.hasPath(5.m(), 2.m()))
        assertTrue(graph.hasPath(4.m(), 3.m()))
        assertFalse(graph.hasPath(6.m(), 5.m()))
        assertTrue(graph.hasPath(6.m(), 6.m()))
    }

}