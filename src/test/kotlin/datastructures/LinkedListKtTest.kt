package datastructures

import org.junit.Test

import org.junit.Assert.*
import utils.getOrThrow
import kotlin.math.exp

class LinkedListKtTest {

    @Test
    fun set() {
        val expected = LinkedList(1,2,10,4,5)
        val result = LinkedList(1..5).set(2,10)
        assertEquals(expected, result)
    }

    @Test
    fun take() {
        val expected = LinkedList(1..2)
        val result = LinkedList(1..5).take(2)
        assertEquals(expected, result)
    }

    @Test
    fun drop() {
        val expected = LinkedList(3..5)
        val result = LinkedList(1..5).drop(2)
        assertEquals(expected, result)
    }

    @Test
    fun fold() {
        val result = LinkedList(1..5).fold(0){next, acc -> next + acc}
        assertEquals(15, result)
    }

    @Test
    fun dropWhile() {
        val expected = LinkedList(5..10)
        val result = LinkedList(1..10).dropWhile { it < 5 }
        assertEquals(expected, result)
    }

    @Test
    fun append() {
        val expected = LinkedList(0..10)
        val result = LinkedList(0..5).append(LinkedList(6..10))
        assertEquals(expected, result)
    }

    @Test
    fun reverse() {
        val expected = LinkedList(5 downTo  0)
        val result = LinkedList(0..5).reverse()
        assertEquals(expected, result)
    }

    @Test
    fun prepend() {
        val expected = LinkedList(0..5)
        val result = LinkedList(1..5).prepend(0)
        assertEquals(expected, result)
    }

    @Test
    fun map() {
        val expected = LinkedList(10,20,30,40,50)
        val result = LinkedList(1..5).map { it * 10 }
        assertEquals(expected, result)
    }

    @Test
    fun filter() {
        val expected = LinkedList(2,4,6,8,10)
        val result = LinkedList(1..10).filter { it % 2 == 0 }
        assertEquals(expected, result)
    }

    @Test
    fun indexOf(){
        val result = LinkedList(1..5).indexOf(2)
        assertEquals(1, result)
    }

    @Test
    fun iterator(){
        val expected = listOf(1,2,3,4,5).iterator()
        val result = LinkedList(1..5).iterator()
        while(expected.hasNext()){
            assertEquals(expected.next(), result.next())
        }
    }

    @Test
    fun size(){
        val result = LinkedList(1..5).size
        assertEquals(5, result)
    }

    @Test
    fun first(){
        val result = LinkedList(1..5).first { it % 2 == 0 }.getOrThrow()
        assertEquals(2, result)
    }

    @Test
    fun last(){
        val result = LinkedList(1..5).last { it % 2 == 0 }.getOrThrow()
        assertEquals(4, result)
    }

    @Test
    fun contains(){
        assertTrue(LinkedList(1..5).contains(2))
        kotlin.test.assertFalse { LinkedList(1..5).contains(6) }
    }

    @Test
    fun removeDupes(){
        val expected = LinkedList(1,2,3,4,5,6)
        val result = LinkedList(1,2,2,3,4,3,5,6,6).removeDupes()
        assertEquals(expected, result)
    }

    @Test
    fun isPalindrome(){
        assertTrue(LinkedList(*"tacocat".toList().toTypedArray()).isPalindrome())
        assertFalse(LinkedList(*"nathan".toList().toTypedArray()).isPalindrome())
    }
}