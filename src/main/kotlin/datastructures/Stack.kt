package datastructures

import utils.Option
import utils.Try
import utils.some
import utils.toOption

class Stack<T> {

    private var list = LinkedList<T>()

    fun push(item : T) : Stack<T> {
        list = LinkedList(item, list)
        return this
    }

    fun pop() : Option<T> = Try{
        list.car
    }.toOption().also { list = list.cdr }

    fun toList() : LinkedList<T> {
        return list
    }
}