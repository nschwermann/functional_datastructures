package datastructures

import org.junit.Test

import org.junit.Assert.*

class HashSetTest {

    @Test
    fun testOrder(){
        val expected = BinTree(10, BinTree(5, BinTree(4), BinTree(7, BinTree(6), BinTree(9))), BinTree(11))
        val result = HashSet(listOf(10,5,7,4,6,11,9)).toTree()
        assertEquals(expected, result)
    }

    @Test
    fun contains() {
        val set = HashSet(listOf(10,5,7,4,6,11,9))
        assertTrue(set.contains(10))
        assertTrue(set.contains(5))
        assertTrue(set.contains(7))
        assertTrue(set.contains(4))
        assertTrue(set.contains(6))
        assertTrue(set.contains(11))
        assertTrue(set.contains(9))
        assertFalse(set.contains(100))
    }

    @Test
    fun testToString(){
        val set = HashSet(listOf(1,2,3))
        assertEquals("[1, 2, 3]", set.toString())
    }
}