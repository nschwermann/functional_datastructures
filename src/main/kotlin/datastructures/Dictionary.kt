package datastructures

import utils.Option

class Dictionary<KEY , VALUE>{

    private var tree : BinTree<Cons<KEY, VALUE>> = BinTree()

    companion object{
        operator fun <A ,B> invoke(pairs : Collection<Pair<A, B>>) : Dictionary<A, B> {
            return Dictionary<A, B>().apply{
                pairs.forEach { set(it.first, it.second)}
            }
        }
    }

    fun getKeys() : LinkedList<KEY> {
        return tree.fold(BinTree.Order.PreOrder, LinkedList.empty()){next, acc -> acc.append(next.car)}
    }

    fun getValues() : LinkedList<VALUE> {
        return tree.fold(BinTree.Order.PreOrder, LinkedList.empty()){next, acc -> acc.append(next.cdr)}
    }

    fun toTree() : BinTree<Cons<KEY, VALUE>>{
        return tree
    }

    operator fun set(key : KEY, value : VALUE){
        tree = insert(tree, key, value)
    }

    operator fun get(key : KEY) : Option<VALUE> {
        return search(tree, key)
    }

    operator fun contains(key : KEY) : Boolean{
        return search(tree, key) !is Option.None
    }

    private fun insert(tree: BinTree<Cons<KEY, VALUE>>, key: KEY, value: VALUE) : BinTree<Cons<KEY, VALUE>> = when(tree){
        is BinTree.Leaf -> BinTree.Branch(Cons(key, value), BinTree.Leaf, BinTree.Leaf)
        is BinTree.Branch -> when{
            key.hashCode() < tree.root.car.hashCode() -> BinTree.Branch(tree.root, insert(tree.left, key, value), tree.right)
            key.hashCode() > tree.root.car.hashCode() -> BinTree.Branch(tree.root, tree.left, insert(tree.right, key, value))
            else -> BinTree.Branch(Cons(key, value), tree.left, tree.right)
        }
    }

    private fun search(tree: BinTree<Cons<KEY, VALUE>>, key: KEY) : Option<VALUE> = when(tree){
        is BinTree.Leaf -> Option.None
        is BinTree.Branch -> when{
            tree.root.car == key -> Option.Some(tree.root.cdr)
            key.hashCode() < tree.root.car.hashCode() -> search(tree.left, key)
            else -> search(tree.right, key)
        }
    }

    override fun toString(): String {
        return buildString {
            append('[')
            tree.fold(BinTree.Order.PreOrder, Unit){next, _ -> append('(').append(next.car).append(',').append(next.cdr).append(')')}
            append(']')
        }
    }

}

