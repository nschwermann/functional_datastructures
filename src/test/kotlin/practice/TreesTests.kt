package practice

import datastructures.BinTree
import datastructures.LinkedList
import datastructures.fold
import datastructures.insertLevelOrder
import org.junit.Test
import kotlin.test.assertEquals

class TreesTests {

    @Test
    fun testViewFromRight(){
        val expected = LinkedList(9,11,4)
        val result = LinkedList(9,5,11,3,4).fold(BinTree<Int>()){next, acc ->
            acc.insertLevelOrder(next)
        }.viewFromRight()
        assertEquals(expected, result)
    }

}