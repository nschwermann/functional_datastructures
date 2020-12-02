package practice

import datastructures.*
import sun.invoke.empty.Empty
import utils.*


/**
 * Given a tree print a list as seen from the right side.
 * Example
 *        9  <---
 *     5    11  <---
 *   3   4 <----
 *
 *   Expected [9, 11, 4]
 */
fun <T> BinTree<T>.viewFromRight() : LinkedList<T>{
    if(this !is BinTree.Branch) return LinkedList.Empty
    var list = LinkedList.empty<T>()
    val que = Queue<Cons<BinTree.Branch<T>, Int>>() //Node to depth
    que.add(this cons 0)
    while(que.peek() !is Option.None){
        val (next,lvl) = que.remove().getOrThrow()
        if(que.peek() is Empty) list = list.append(next.root)
        else if(que.peek().map { it.cdr }.getOrNull()!= lvl) list = list.append(next.root)
        if(next.left is BinTree.Branch) que.add(next.left cons lvl + 1)
        if(next.right is BinTree.Branch) que.add(next.right cons lvl + 1)
    }
    return list
}