package datastructures


class Matrix constructor(
    private val height: Int,
    private val width: Int) {

    private val array : IntArray = IntArray(width * height)

    fun insert(vararg values: Int){
        if(values.size != width*height) throw IllegalArgumentException()
        values.forEachIndexed { index, i ->
            val (col, row) = convert2D(index)
            set(i, row, col)
        }
    }

    fun insert(intRange: IntRange) {
        insert(*intRange.toList().toIntArray())
    }

    fun set(value : Int, row : Int, col : Int){
        array[convert1D(col, row)] = value
    }

    fun get(row : Int, col : Int) : Int = array[convert1D(col, row)]

    private fun convert1D(col: Int, row: Int) : Int = row * width + col
    //col x row
    private fun convert2D(index : Int) : Pair<Int, Int> {
        return index % width to index / width
    }

    override fun toString(): String {
        return buildString {
            for(row in 0 until  height){
                for (col in 0 until  width){
                    append(get(row, col))
                    if(col != width - 1) append(' ')
                }
                if(row != height -1)append('\n')
            }
        }
    }

    fun toArray() : IntArray{
        return array.copyOf()
    }

    fun rotate90() : Matrix{
        return Matrix(height, width).also {
            array.forEachIndexed { index, i ->
                val (col, row) = convert2D(index)
                val newC = width - row - 1
                it.set(i, col, newC)
            }
        }
    }
}