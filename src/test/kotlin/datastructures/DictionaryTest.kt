package datastructures

import org.junit.Test

import org.junit.Assert.*
import utils.Option
import utils.getOrThrow

class DictionaryTest {

    private val dictionary = Dictionary<Int, String>().apply {
        set(1, "One")
        set(2, "Two")
        set(3, "Three")
    }

    @Test
    fun getKeys() {
        val expected = LinkedList(1, 2, 3)
        assertEquals(expected, dictionary.getKeys())
    }

    @Test
    fun getValues() {
        val expected = LinkedList("One", "Two", "Three")
        assertEquals(expected, dictionary.getValues())
    }

    @Test
    fun get() {
        assertEquals("One", dictionary[1].getOrThrow())
        assertEquals("Two", dictionary[2].getOrThrow())
        assertEquals("Three", dictionary[3].getOrThrow())
        assertEquals(Option.None, dictionary[4])
    }

    @Test
    fun delete(){
        dictionary.delete(2)
        assertTrue(dictionary.contains(1))
        assertTrue(dictionary.contains(3))
        assertFalse(dictionary.contains(2))
    }

    @Test
    fun contains() {
        assertTrue(dictionary.contains(1))
        assertTrue(dictionary.contains(2))
        assertTrue(dictionary.contains(3))
    }
}