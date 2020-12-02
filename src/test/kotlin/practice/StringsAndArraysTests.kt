package practice

import org.junit.Assert.*
import org.junit.Test

class StringsAndArraysTests{

    @Test
    fun uniqueCharactersTests(){
        assertTrue("cat".allUnique())
        assertFalse("buttercup".allUnique())
        assertFalse("nathan".allUnique())
        assertTrue("abcdefghijklmnopqrstuvwxyz".allUnique())
        assertTrue("N".allUnique())
        assertTrue("".allUnique())
        assertFalse("aaaaa".allUnique())
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun isPermutationTests(){
        assertTrue("abc".isPermutation("bac"))
        assertFalse("abc".isPermutation("abd"))
        assertTrue("abc".isPermutation("abc"))
        assertTrue("abcd".isPermutation("dcba"))
        assertFalse("abcd".isPermutation("abcdd"))
    }

    @Test
    fun isPalindrome(){
        assertTrue("tacocat".isPalindrome())
        assertTrue("naan".isPalindrome())
        assertFalse("nathan".isPalindrome())
    }

    @Test
    fun isPalindromPermuation(){
        assertTrue("Tact Coa".isPalindromePermutation())
        assertFalse("Nathan".isPalindromePermutation())
        assertTrue("Taco Cat".isPalindromePermutation())
        assertTrue("tactcoapapa".isPalindromePermutation())
    }

    @Test
    fun oneAway(){
        assertTrue("pale".oneAway("ple"))
        assertTrue("pales".oneAway("pale"))
        assertTrue("pale".oneAway("pales"))
        assertTrue("pale".oneAway("bale"))
        assertFalse("pale".oneAway("bae"))
        assertTrue("aaaab".oneAway("aaab"))
        assertFalse("aaaaa".oneAway("aaa"))
        assertFalse("aaaaa".oneAway("baaa"))
        assertTrue("aaaa".oneAway("baaa"))
        assertTrue("baaa".oneAway("aaaa"))
        assertTrue("baaa".oneAway("baaaa"))
        assertTrue("apple".oneAway("aple"))
        assertTrue("ab".oneAway("aab"))
    }

    @Test
    fun subStringRotation(){
        assertTrue("waterbottle".isRotation("erbottlewat"))
    }

    @Test
    fun mergeArrays(){
        val a = intArrayOf(1, 4, 7, 10, 0, 0, 0, 0)
        val b = intArrayOf(3, 5, 6, 9)
        val expected = intArrayOf(1,3,4,5,6,7,9,10)
        mergeArrays(a,b)
        assertArrayEquals(expected, a)
    }
}