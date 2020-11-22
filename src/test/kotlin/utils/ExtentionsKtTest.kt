package utils

import org.junit.Test

import org.junit.Assert.*

class ExtentionsKtTest {

    @Test
    fun powerset3() {
        val expected : Set<Set<Int>> = setOf(emptySet(), setOf(1), setOf(2), setOf(3), setOf(1,2), setOf(1,3), setOf(2,3), setOf(1,2,3))
        assertEquals(expected, listOf(1,2,3).powerset())
        assertEquals(expected, listOf(1,2,3).powerset3())
    }
}