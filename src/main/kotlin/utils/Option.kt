package utils

sealed class Option<out A>{
    data class Some<A>(val value : A) : Option<A>()
    object None : Option<Nothing>() {
        override fun toString(): String {
            return "None"
        }
    }

    override fun equals(other: Any?): Boolean {
        return when{
            other !is Option<*> -> false
            this is None && other is None -> true
            this is Some<*> && other is Some<*> -> value == other.value
            else -> false
        }
    }

    override fun hashCode(): Int = when(this){
        is None -> None.hashCode()
        is Some -> value.hashCode()
    }
}

fun <A, B> Option<A>.map(f : (A) -> B) : Option<B> = when(this){
    is Option.Some -> Option.Some(f(value))
    else -> Option.None
}

fun <A> Option<A>.forEach(f : (A) -> Unit) : Unit = when(this){
    is Option.Some -> f(value)
    else -> Unit
}

fun <A, B> Option<A>.fMap(f : (A) -> Option<B>) : Option<B> = map(f).orElse { Option.None }

fun <A> Option<A>.orElse(block : () -> A) : A = when(this){
    is Option.Some -> value
    else -> block()
}

fun <A> Option<A>.getOrElse(default: A) : A = orElse { default }

fun <A> Option<A>.getOrThrow() : A = orElse {
    throw IllegalStateException()
}

fun <A> Option<A>.getOrNull() : A? = orElse { null }

fun <A> Option<A>.filter(pred : (A) -> Boolean) : Option<A> = fMap {
    if(pred(it)) this else Option.None
}

fun <A, B> Option<A>.lift(f : (A) -> B) : Option<B> = map(f)

fun < T> T?.some() = when{
    this == null -> Option.None
    else -> Option.Some(this)
}

fun <T, R> Option<T>.reduce(f : (T?) -> R) : R = when(this){
    is Option.Some -> f(value)
    else -> f(null)
}

fun <T> Try<T>.toOption() : Option<T> = when(this){
    is Try.Success -> Option.Some(data)
    else -> Option.None
}
