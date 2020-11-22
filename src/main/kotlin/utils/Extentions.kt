package utils

fun <T> Collection<T>.powerset(): Set<Set<T>> {
    return when {
        isEmpty() -> setOf(setOf())
        else -> drop(1).powerset().let { nextSet ->
            nextSet + nextSet.map {
                it + first()
            }
        }
    }
}

private tailrec fun <T> powerset(left: Collection<T>, acc: Set<Set<T>>): Set<Set<T>> = when {
    left.isEmpty() -> acc
    else ->powerset(left.drop(1), acc + acc.map { it + left.first() })
}

fun <T> Collection<T>.powerset3() : Set<Set<T>> = fold(setOf(setOf())){acc, next ->
    acc + acc.map { it + next }
}

