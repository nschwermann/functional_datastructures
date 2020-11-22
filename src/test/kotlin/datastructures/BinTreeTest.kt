package datastructures

import org.junit.Test

import org.junit.Assert.*
import kotlin.math.exp
import kotlin.test.asserter

class BinTreeTest {

    @Test
    fun getSize() {
        assertEquals(1, BinTree(1).size)
        assertEquals(0, BinTree.Leaf.size)
        assertEquals(3, BinTree(1, BinTree(2), BinTree(3)).size)
    }

    @Test
    fun getDepth() {
        assertEquals(0, BinTree.Leaf.depth)
        assertEquals(1, BinTree(1).depth)
        assertEquals(3, BinTree.buildCompleteTree(1, 3).depth)
    }

    @Test
    fun isEmpty() {
        kotlin.test.assertTrue { BinTree<Int>().isEmpty() }
    }

    @Test
    fun testEquals() {
        kotlin.test.assertTrue {
            BinTree.buildCompleteTree(1,3) == BinTree(1, BinTree(2, BinTree(4), BinTree(5)), BinTree(3, BinTree(6), BinTree(7)))
        }
    }

    @Test
    fun traversals(){
        val acc : (Int, LinkedList<Int>) -> LinkedList<Int> = { next, acc ->
            acc.append(next)
        }
        val tree = BinTree.Branch(1, BinTree.Branch(2, BinTree.Branch(4), BinTree.Branch(5)),BinTree.Branch(3))
        val inOrder = LinkedList(4, 2, 5, 1, 3)
        val preOrder = LinkedList(1, 2, 4, 5, 3)
        val postOrder = LinkedList(4, 5, 2, 3, 1)
        val lvlOrder = LinkedList(1,2,3,4,5)

        val orderFold = tree.fold(BinTree.Order.InOrder, LinkedList.empty(), acc)
        val preFold = tree.fold(BinTree.Order.PreOrder, LinkedList.empty(), acc)
        val postFold = tree.fold(BinTree.Order.PostOrder, LinkedList.empty(), acc)
        val lvlFold = tree.foldLevelOrder(LinkedList.empty(), acc)

        assertEquals(inOrder, orderFold)
        assertEquals(preOrder, preFold)
        assertEquals(postOrder, postFold)
        assertEquals(lvlOrder, lvlFold)
    }

    @Test
    fun insertLevelOrder(){
        val lvlOrder = LinkedList(1..7).fold(BinTree<Int>()){next, acc -> acc.insertLevelOrder(next)}
        assertEquals(BinTree.buildCompleteTree(1, 3), lvlOrder)
    }

    @Test
    fun findDeepestRightMostBranch(){
        val result = BinTree(10, BinTree(20, BinTree(40), BinTree.Leaf), BinTree(30, BinTree(50), BinTree.Leaf)).deepestRightMostBranch().first()
        assertEquals(50, result)
        val result2 = BinTree(10, BinTree(20, BinTree(40), BinTree.Leaf), BinTree(30)).deepestRightMostBranch().first()
        assertEquals(40, result2)
    }

    @Test
    fun testDelete(){
        val expected = BinTree(10, BinTree(40), BinTree(30))
        val case1 = BinTree(10, BinTree(20, BinTree(40),BinTree.Leaf), BinTree(30)).delete(20)
        val case2 = BinTree(10, BinTree(20), BinTree(30, BinTree.Leaf, BinTree(40))).delete(20)
        assertEquals(expected, case1)
        assertEquals(expected, case2)
    }

    @Test
    fun testIsCompleteTree(){
        assertTrue(BinTree.buildCompleteTree(1,3).isCompleteTree())
    }

    @Test
    fun testMap(){
        val expected = BinTree(10, BinTree(20), BinTree(30))
        val result = BinTree(1, BinTree(2), BinTree(3)).map { it*10 }
        assertEquals(expected, result)
    }

    @Test
    fun testSum(){
        val result = BinTree.buildCompleteTree(1, 3).sum()
        assertEquals(28, result)
    }
}