package utils

import org.junit.Assert.*
import org.junit.Test

class TryTest{

    @Test
    fun catch(){
        val myMessage = "My Message"
        val t = Try{
            throw RuntimeException(myMessage)
        }
        assertTrue(t is Try.Fail)
        assertTrue(t.isFail())
        try{
            t.getOrThrow()
        }catch (e : Exception){
            assertTrue(e is RuntimeException)
            assertEquals(myMessage, e.message)
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
}