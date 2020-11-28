package datastructures

import utils.Option
import utils.some

sealed class LinkedList<out T> : Cons<T, LinkedList<T>> {

    object Empty : LinkedList<Nothing>()
    private class Node<T>(val head: T, val tail : LinkedList<T>) : LinkedList<T>()

    companion object {

        operator fun <T> invoke(vararg args : T) : LinkedList<T> =  when{
            args.isEmpty() -> Empty
            else -> Node(args.first(), invoke(*args.sliceArray(1 until args.size)))
        }
        operator fun <T> invoke(head : T, tail : LinkedList<T> ) : LinkedList<T> = Node(head, tail)
        operator fun <T> invoke(cons : Cons<T, LinkedList<T>>) : LinkedList<T> = Node(cons.car, cons.cdr)
        operator fun invoke(range : IntRange) : LinkedList<Int> = invoke(*range.toList().toTypedArray())
        operator fun invoke(progression: IntProgression) : LinkedList<Int> = invoke(*progression.toList().toTypedArray())
        operator fun <T> invoke() : LinkedList<T> = Empty
        fun <T> empty() : LinkedList<T> = Empty
    }

    fun contains(element: @UnsafeVariance T): Boolean = when(this){
        is Empty -> false
        is Node -> if(head == element) true else tail.contains(element)
    }

    fun indexOf(element: @UnsafeVariance T): Int = when(this){
        is Empty -> throw IllegalArgumentException()
        is Node -> if(head == element) 0 else tail.indexOf(element) + 1
    }

    fun iterator(): Iterator<T> = iterator {
        var next : LinkedList<T> = this@LinkedList
        while(true){
            val cur = next
            if(cur is Empty) break
            else{
                yield(cur.car)
                next = cur.cdr
            }
        }
    }

    val size : Int
        get() = fold(0){ _, acc -> acc + 1}

    fun isEmpty() = this is Empty

    override val car : T
        get() =when(this){
            is Empty -> throw IllegalStateException()
            is Node -> head
        }

    override val cdr : LinkedList<T>
        get() = when(this){
            is Empty -> this
            is Node -> tail
        }

    operator fun get(index : Int) : T = when(this){
        is Empty -> throw IllegalArgumentException()
        is Node -> if(index == 0) head else tail[index - 1]
    }

    override fun toString(): String {
        return StringBuilder().apply {
            append('[')
            buildString(this)
            append(']')
        }.toString()
    }


    private fun buildString(builder : StringBuilder){
        if(this is Node){
            builder.append(head)
            if(tail is Node) {
                builder.append(',')
                builder.append(' ')
                tail.buildString(builder)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return when{
            other !is Cons<*,*> -> false
            this is Empty && other is Empty -> true
            this !is Empty && other !is Empty ->{
                this.car == other.car && this.cdr == other.cdr
            }
            else -> false
        }
    }

    override fun hashCode(): Int = fold(7){ next, hash -> hash + next.hashCode()} * 37

}

fun <T> LinkedList<T>.set(index: Int, value : T) : LinkedList<T> = when(this){
    is LinkedList.Empty -> throw IllegalArgumentException()
    else -> if(index == 0)
                    LinkedList(value, cdr)
                  else
                     LinkedList(car, cdr.set(index - 1, value))
}

fun <T> LinkedList<T>.take(n : Int) : LinkedList<T> = when{
    n < 1 -> LinkedList.Empty
    this is LinkedList.Empty -> throw IllegalArgumentException()
    else -> LinkedList(car, cdr.take(n-1))
}

tailrec fun <T> LinkedList<T>.drop(n : Int) : LinkedList<T> = when(this){
    is LinkedList.Empty ->  LinkedList.Empty
    else -> if(n == 0) this else cdr.drop(n - 1)
}

tailrec fun <T, R> LinkedList<T>.fold(initial : R, acc : (T, R) -> R) : R = when(this){
    is LinkedList.Empty -> initial
    else -> cdr.fold(acc(car, initial), acc)
}

tailrec fun <T, R> LinkedList<T>.fold2(initial: R, acc : (LinkedList<T>, R) -> R) : R = when(this){
    is LinkedList.Empty -> acc(this, initial)
    else -> cdr.fold2(acc(this, initial), acc)
}

fun <T> LinkedList<T>.dropWhile(pred : (T) -> Boolean) : LinkedList<T> = when(this){
    is LinkedList.Empty -> LinkedList.Empty
    else -> if(pred(car)) cdr.dropWhile(pred) else this
}

fun <T> LinkedList<T>.append(b : LinkedList<T>) : LinkedList<T> = when(this){
    is LinkedList.Empty -> b
    else -> LinkedList(car, cdr.append(b))
}

fun <T> LinkedList<T>.append(value : T) : LinkedList<T> = when(this){
    is LinkedList.Empty -> LinkedList(value)
    else -> LinkedList(car, cdr.append(value))
}

fun <T> LinkedList<T>.reverse() : LinkedList<T> = fold(LinkedList.empty()){ next, acc -> LinkedList(next, acc)}

fun <T> LinkedList<T>.prepend(value : T) : LinkedList<T> = LinkedList(value, this)

fun<T,R> LinkedList<T>.map(f : (T) -> R) : LinkedList<R> = when(this){
    is LinkedList.Empty -> this
    else -> LinkedList(f(car), cdr.map(f))
}

fun <T,R> LinkedList<T>.flatMap(f : (T) -> LinkedList<R>) : LinkedList<R> = when(this){
    is LinkedList.Empty -> this
    else -> f(car).append(cdr.flatMap(f))
}

fun <T> LinkedList<T>.filter(f : (T) -> Boolean) : LinkedList<T> = flatMap {
    if(f(it)) LinkedList(it)
    else LinkedList.empty()
}

fun <T> LinkedList<T>.first(f : (T) -> Boolean) : Option<T> = when(this){
    is LinkedList.Empty -> Option.None
    else -> if(f(car)) Option.Some(car) else cdr.first(f)
}

fun <T> LinkedList<T>.last(f : (T) -> Boolean) : Option<T> = fold(Option.None as Option<T>){next, acc ->
    if(f(next)) next.some()
    else acc
}

fun LinkedList<Int>.sum() = fold(0){next, acc -> next + acc}

fun <T>LinkedList<T>.removeDupes() : LinkedList<T> = reverse().fold2(LinkedList.empty()){next, acc ->
    when(next){
        is LinkedList.Empty -> acc
        else -> if(!next.cdr.contains(next.car)) acc.prepend(next.car) else acc
    }
}

fun <T> LinkedList<T>.isPalindrome() : Boolean {
    val firstHalf = take(size/2)
    val secondHalf = reverse().take(size/2)
    return firstHalf == secondHalf
}