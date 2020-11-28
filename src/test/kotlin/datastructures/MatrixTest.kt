package datastructures

import org.junit.Assert.*
import org.junit.Test

class MatrixTest{

    @Test
    fun testToString(){
        val expected = """
            1 2 3
            4 5 6
            7 8 9
        """.trimIndent()
        val result = Matrix(3,3).apply {
            insert(1..9)
        }.toString()
        assertEquals(expected, result)
    }

    @Test
    fun testInsert(){
        val expected = intArrayOf(1,2,3,4)
        val result = Matrix(2,2).apply {
            insert(1,2,3,4)
        }.toArray()
        assertArrayEquals(expected, result)
    }

    @Test
    fun testRotate90(){
        val expected = intArrayOf(3,1,4,2)
        val result = Matrix(2,2).apply {
            insert(1..4)
        }.rotate90().toArray()
        assertArrayEquals(expected, result)
    }

    @Test
    fun testRotate90_3by3(){
        val expected = intArrayOf(7,4,1,8,5,2,9,6,3)
        val result = Matrix(3,3).apply {
            insert(1..9)
        }.rotate90().toArray()
        assertArrayEquals(expected, result)
    }
}