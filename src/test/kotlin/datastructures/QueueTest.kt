package datastructures

import org.junit.Test

import org.junit.Assert.*
import utils.Option
import utils.some

class QueueTest {

    @Test
    fun add() {
        val queue = Queue<Int>().add(1).add(2).add(3)
        assertEquals(LinkedList(1..3), queue.toList())
    }

    @Test
    fun peek() {
        val queue = Queue<Int>()
        assertEquals(Option.None, queue.peek())
        queue.add(1).add(2).add(3)
        assertEquals(1.some(), queue.peek())
    }

    @Test
    fun remove() {
        val queue = Queue<Int>().add(1)
        assertEquals(1.some(), queue.remove())
        assertEquals(Option.None, queue.remove())
    }

    @Test
    fun listOrder(){
        val expected = LinkedList(1..3)
        val result = Queue<Int>().add(1).add(2).add(3)
        assertEquals(expected, result.toList())
    }
}