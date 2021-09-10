package utils

import org.junit.Assert.*
import org.junit.Test
import kotlin.math.exp
import kotlin.test.assertFailsWith

class TryTest{

    @Test
    fun catch(){
        val myMessage = "My Message"
        val t = Try{
            throw RuntimeException(myMessage)
        }
        assertTrue(t is Try.Fail)
        assertTrue(t.isFail())
        assertFailsWith<RuntimeException>(myMessage){
            t.getOrThrow()
        }
    }

    @Test
    fun map(){
        val t = Try{
            1
        }.map { it * 2 }
        assertEquals(2, t.getOrThrow())
        val t2 = t.map {
            it/0
        }.map {
            1
        }
        assertTrue(t2 is Try.Fail)
    }

    @Test
    fun fMap(){
        val t = Try{
            1 + 1
        }.fMap {
            Try{
                throw RuntimeException()
            }
        }.map {
            1
        }
        assertTrue(t is Try.Fail)
    }

    @Test
    fun filterNull(){
        val isNull = Try<Int?>{
            null
        }.getOrThrow()
        assertEquals(null, null, isNull)
        val isNotNull = Try<Int?>{
            null
        }.filterNull()
        assertFailsWith<NullPointerException> {
            isNotNull.getOrThrow()
        }
    }

    @Test
    fun getOrDefault(){
        val expected = 1
        val result = Try<Int>{
            throw Exception()
        }.getOrDefault(expected)
        assertEquals(null, expected, result)
    }

    @Test
    fun onSuccess(){
        var count = 0
        Try{}.onSuccess { count++ }
        Try{throw Exception()}.onSuccess { count++ }
        assertEquals(null, 1, count)
    }

    @Test
    fun onFail(){
        var count = 0
        Try{}.onFail { count++ }
        Try{throw Exception()}.onFail { count++ }
        assertEquals(null, 1, count)
    }

    @Test
    fun finally(){
        var count = 0
        Try{}.finally { count++ }
        Try{throw Exception()}.finally { count++ }
        assertEquals(null, 2, count)
    }
}