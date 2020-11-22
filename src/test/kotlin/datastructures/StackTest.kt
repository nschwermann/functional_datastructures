package datastructures

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import utils.Option
import utils.getOrThrow

class StackTest {


    @Test
    fun push() {
        val stack = Stack<Int>().push(1).push(2).push(3)
        assertEquals(LinkedList(3 downTo 1), stack.toList())
    }

    @Test
    fun pop() {
        val stack = Stack<Int>().push(1).push(2).push(3)
        assertEquals(3, stack.pop().getOrThrow())
        assertEquals(LinkedList(2,1), stack.toList())
    }

    @Test
    fun empty(){
        val stack = Stack<Int>()
        assertEquals(LinkedList.Empty, stack.toList())
        assertEquals(Option.None, stack.pop())
    }

}