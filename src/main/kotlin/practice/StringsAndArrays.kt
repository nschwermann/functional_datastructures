package practice

import datastructures.Dictionary
import datastructures.map
import datastructures.sum
import utils.getOrElse
import utils.map
import kotlin.math.abs

fun String.allUnique() : Boolean = when {
    isEmpty() || length == 1 -> true
    else -> substring(1).let { !it.contains(first()) && it.allUnique() }
}

@ExperimentalStdlibApi
fun String.isPermutation(other : String) : Boolean {
    if(length != other.length) return false
    if(this == other) return true

    val builder : (String) ->  Map<Char, Int>  = {
        buildMap{ it.forEach { char -> put(char, get(char)?.plus(1) ?: 1) } }
    }

    return builder(this) == builder(other)
}

fun String.isPalindrome() : Boolean{
    for(i in 0 .. length/2){
        if(get(i) != get(length - i - 1)) return false
    }
    return true
}

fun String.isPalindromePermutation() : Boolean{
    return Dictionary<Char, Int>().apply {
        toLowerCase().forEach {char ->
            if(char != ' '){
                set(char, get(char).map { it + 1 }.getOrElse(1))
            }
        }
    }.getValues().map { it % 2 }.sum() <= 1
}

/**
 * Return true if other string is one edit (insert, remove, replace) away from the other.
 */
fun String.oneAway(other : String) : Boolean{
    if(this == other) return true
    val difference = abs(length - other.length)
    if(difference > 1) return false
    val longest = if(length > other.length) this else other
    var shortest = if(this == longest) other else this
    var foundDiff = false
    for(i in longest.indices){
        if(shortest.getOrNull(i) == null) return !foundDiff //reached end of shortest, if no previous diff just need to add a character
        if(longest[i] == shortest[i]) continue
        if(longest.getOrNull(i + 1) == shortest[i]){
            if(foundDiff) return false
            shortest = shortest.take(i) + longest[i] + shortest.substring(i)
            foundDiff = true
            continue
        }
        if(foundDiff) return false
        else foundDiff = true
    }
    return true
}

//Check if other string is a rotation of first using only 'isSubString'
//ie)waterbottle rotation -> erbottlewat
fun String.isRotation(other : String) : Boolean{
    fun String.isSubString(other : String) = contains(other, ignoreCase = false)
    return if(length == other.length && length > 0){
        "$other$other".isSubString(this)
    } else {
        false
    }
}