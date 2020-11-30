package datastructures

import utils.Option
import utils.some

class Queue<T>{

    private var linkedList : LinkedList<T> = LinkedList.empty()

    fun add(data : T) : Queue<T> = apply {
        linkedList = when{
            linkedList.isEmpty() -> LinkedList(data)
            else -> linkedList.append(data)
        }
    }

    fun peek() : Option<T> = when(linkedList){
        is LinkedList.Empty -> Option.None
        else -> linkedList.car.some()
    }

    fun remove() : Option<T> = peek().also { linkedList = linkedList.cdr }

    fun toList() : LinkedList<T>{
        return linkedList
    }

    override fun toString(): String {
        return linkedList.toString()
    }

}