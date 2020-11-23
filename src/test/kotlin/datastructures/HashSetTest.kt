package datastructures

import org.junit.Test

import org.junit.Assert.*

class HashSetTest {

    @Test
    fun testOrder(){
        val expected = BinTree(10, BinTree(5, BinTree(4), BinTree(7, BinTree(6), BinTree(9))), BinTree(11))
        val result = HashSet(10,5,7,4,6,11,9).toTree()
        assertEquals(expected, result)
    }

    @Test
    fun contains() {
        val set = HashSet(10,5,7,4,6,11,9)
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
    fun delete(){
        val expected = BinTree(60, BinTree(40), BinTree(70, BinTree.Leaf, BinTree(80)))
        val set = HashSet(50,40,70,60,80)
        set.remove(50)
        assertEquals(expected, set.toTree())
    }

    @Test
    fun delete2(){
        val expected = BinTree(50, BinTree(30, BinTree.Leaf, BinTree(40)), BinTree(70, BinTree(60), BinTree(80)))
        val result = HashSet(50,30,20,40,70,60,80).apply {
            remove(20)
        }.toTree()
        assertEquals(expected, result)
    }

    @Test
    fun delete3(){
        val expected = BinTree(50, BinTree(40), BinTree(70, BinTree(60), BinTree(80)))
        val result = HashSet(50, 30, 40, 70, 60, 80).apply {
            remove(30)
        }.toTree()
        assertEquals(expected, result)
    }

    @Test
    fun testToString(){
        val set = HashSet(1,2,3)
        assertEquals("[1, 2, 3]", set.toString())
    }
}