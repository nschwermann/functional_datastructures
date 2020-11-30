package datastructures

import utils.getOrThrow
import kotlin.math.max
import kotlin.math.pow

sealed class BinTree<out A>{

    enum class Order{
        PreOrder, InOrder, PostOrder
    }

    companion object {

        operator fun <A> invoke(list : LinkedList<A>) : BinTree<A> = when{
            list.isEmpty() -> Leaf
            else -> {
                val k = list.size/2
                Branch(list.car, invoke(list.cdr.take(k)) , invoke(list.cdr.drop(k)))
            }
        }

        operator fun <A> invoke() : BinTree<A> = Leaf

        operator fun <A> invoke(data : A, left :BinTree<A>, right : BinTree<A>) : BinTree<A> = Branch(data, left, right)

        operator fun <A> invoke(data : A) = Branch(data)

        fun buildCompleteTree(v : Int, depth : Int): BinTree<Int> = when (depth) {
            0 -> Leaf
            else -> Branch(v, buildCompleteTree(v*2, depth -1), buildCompleteTree(v*2+1, depth -1))
        }
    }

    object Leaf : BinTree<Nothing>(){
        override fun toString(): String {
            return "🍁"
        }
    }
    data class Branch<A>(val root: A,
                                  val left : BinTree<A> = Leaf,
                                  val right : BinTree<A> = Leaf) : BinTree<A>()


    val size : Int
        get() = when(this){
            is Leaf -> 0
            is Branch -> 1 + left.size + right.size
        }

    val depth : Int
        get() = when(this){
            is Leaf -> 0
            is Branch -> 1 + max(left.depth, right.depth)
        }

    fun isEmpty() : Boolean{
        return size == 0
    }

    fun first() : A = when(this){
        is Leaf -> throw IllegalStateException()
        is Branch -> root
    }

    override operator fun equals(other: Any?): Boolean = when{
        other !is BinTree<*> -> false
        this is Leaf && other is Leaf -> true
        this is Branch && other is Branch<*> -> root == other.root && left == other.left && right == other.right
        else -> false
    }

    override fun hashCode(): Int {
        return fold(Order.PreOrder,7){ next, hash -> next.hashCode() + hash}*37
    }
}

//If wasn't immutable
//fun <A> BinTree<A>.insertLevelOrder(data : A) : BinTree<A> {
//    if(this is BinTree.Leaf) return BinTree.Branch(data)
//    val queue : Queue<BinTree.Branch<A>> = Queue()
//    queue.add(this as BinTree.Branch<A>)
//    while(queue.peek() !is Option.None){
//        val next = queue.remove()
//        when {
//            next.left.isEmpty() ->  next.left = data
//            next.right.isEmpty() -> next.right = data
//            else -> {
//                queue.add(left as BinTree.Branch<A>)
//                queue.add(right as BinTree.Branch<A>)
//            }
//        }
//    }
//}

fun <A> BinTree<A>.insertLevelOrder(data : A) : BinTree<A> = when(this){
    is BinTree.Leaf -> BinTree(data)
    is BinTree.Branch -> {
        when {
            left is BinTree.Leaf -> BinTree(root, BinTree.Branch(data), right)
            right is BinTree.Leaf -> BinTree(root, left, BinTree.Branch(data))
            isCompleteTree() -> BinTree(root, left.insertLevelOrder(data), right)
            left.isCompleteTree() -> BinTree(root, left, right.insertLevelOrder(data))
            else ->  BinTree(root, left.insertLevelOrder(data), right)
        }
    }
}

fun <A> BinTree<A>.isCompleteTree() = foldLevel(depth, 0){ _, acc ->
    acc + 1
}.let {
    it == 2f.pow(depth - 1).toInt()
}

fun <A, B> BinTree<A>.fold(order: BinTree.Order, b : B, acc : (A, B) -> B) : B = when(this){
    is BinTree.Leaf -> b
    is BinTree.Branch -> when(order){
        BinTree.Order.PreOrder -> {
            val visitRoot = acc(root, b)
            val visitLeft = left.fold(order, visitRoot, acc)
            right.fold(order, visitLeft, acc)
        }
        BinTree.Order.InOrder -> {
            val visitLeft = left.fold(order, b, acc)
            val visitRoot = acc(root, visitLeft)
            right.fold(order, visitRoot, acc)
        }
        BinTree.Order.PostOrder -> {
            val visitLeft = left.fold(order, b, acc)
            val visitRight = right.fold(order, visitLeft, acc)
            acc(root, visitRight)
        }
    }
}

fun <A, B> BinTree<A>.foldLevelOrder(b : B, acc : (A, B) -> B) : B = (1..depth).fold(b){next, level ->
    foldLevel(level, next, acc)
}

fun <A, B> BinTree<A>.foldLevel(level : Int, b : B, acc : (A, B) -> B) : B = when(this){
    is BinTree.Leaf -> b
    is BinTree.Branch -> when{
        level == 1 -> acc(root, b)
        level > 1 -> right.foldLevel(level - 1, left.foldLevel(level - 1, b, acc),acc)
        else -> throw IllegalArgumentException()
    }
}

fun <A, B> BinTree<A>.foldLevel2(level : Int, b : B, acc : (BinTree<A>, B) -> B) : B = when(this){
    is BinTree.Leaf -> b
    is BinTree.Branch -> when{
        level == 1 -> acc(this, b)
        level > 1 -> left.foldLevel2(level - 1, b, acc).let { right.foldLevel2(level -1, it, acc) }
        else -> throw IllegalArgumentException()
    }
}

fun  BinTree<Int>.sum() : Int = when(this){
    is BinTree.Leaf -> 0
    is BinTree.Branch -> root + left.sum() + right.sum()
}

fun <A, B>BinTree<A>.map(f : (A) -> B): BinTree<B> = when(this){
    is BinTree.Leaf -> BinTree.Leaf
    is BinTree.Branch -> BinTree.Branch(f(root), left.map(f), right.map(f))
}

fun <A> BinTree<A>.delete(data : A) : BinTree<A> = when(this){
    is BinTree.Leaf -> this
    is BinTree.Branch -> {
        val rmb = deepestRightMostBranch()
        fun helper(tree : BinTree<A>) : BinTree<A> = when(tree){
            is BinTree.Leaf -> tree
            is BinTree.Branch -> when{
                tree.root == data -> BinTree(rmb.root, helper(tree.left), helper(tree.right))
                tree === rmb -> BinTree.Leaf
                else -> BinTree(tree.root, helper(tree.left), helper(tree.right))
            }
        }
        helper(this)
    }
}

fun <A> BinTree<A>.deepestRightMostBranch() : BinTree.Branch<A> = rightMostBranch(depth)

fun <A> BinTree<A>.rightMostBranch(level: Int) : BinTree.Branch<A> = foldLevel2(level, Stack<BinTree.Branch<A>>()){ next, acc ->
    if(next is BinTree.Branch) acc.push(next)
    else acc
}.pop().getOrThrow()