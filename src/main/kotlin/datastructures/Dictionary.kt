package datastructures

import utils.*

class Dictionary<KEY , VALUE>{

    private var tree : BinTree<Cons<KEY, VALUE>> = BinTree()

    companion object{
        operator fun <A ,B> invoke(pairs : Collection<Pair<A, B>>) : Dictionary<A, B> {
            return Dictionary<A, B>().apply{
                pairs.forEach { set(it.first, it.second) }
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
        return search(tree, key).map { it.first().cdr }
    }

    fun delete(key : KEY) {
        if(!contains(key)) return
        val toRemove = search(tree, key).getOrThrow() as BinTree.Branch<Cons<KEY, VALUE>>
        val replacement = when{
            toRemove.size == 1 -> BinTree.Leaf
            toRemove.left is BinTree.Branch && toRemove.right is BinTree.Branch ->
                //Copy contents of the inorder successor to the node and delete the inorder successor.
                //The inorder successor is needed only when right child is not empty. In this particular case,
                //inorder successor can be obtained by finding the minimum value in right child of the node.
                toRemove.right.fold(BinTree.Order.InOrder, toRemove.right){ next, min ->
                    if(next.car.hashCode() < min.root.car.hashCode()) BinTree(next)
                    else min
            }
            toRemove.left is BinTree.Branch -> toRemove.left
            else -> toRemove.right
        }

        fun helper(t: BinTree<Cons<KEY, VALUE>>) : BinTree<Cons<KEY,VALUE>> = when(t){
            is BinTree.Leaf -> t
            is BinTree.Branch -> {
                when{
                    //Rebuilding a new immutable tree, have reached the key we are deleting.
                    //Replace it with the branch or leaf we found above.
                    t.root.car.hashCode() == key.hashCode() -> {
                        if(replacement is BinTree.Leaf) replacement
                        else BinTree(replacement.first(), helper(t.left), helper(t.right))
                    }
                    //If the replacement is a branch make its old spot a leaf
                    replacement is BinTree.Branch && t.root.car.hashCode() == replacement.root.car.hashCode() -> BinTree.Leaf
                    //Trees are immutable. Rebuild a new tree from top down.
                    else -> BinTree(t.root, helper(t.left), helper(t.right))
                }
            }
        }

        tree = helper(tree)
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

    private fun search(tree: BinTree<Cons<KEY, VALUE>>, key: KEY) : Option<BinTree<Cons<KEY, VALUE>>> = when(tree){
        is BinTree.Leaf -> Option.None
        is BinTree.Branch -> when{
            tree.root.car == key -> tree.some()
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

