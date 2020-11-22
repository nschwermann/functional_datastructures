package utils

sealed class Try<out T> {

    data class Fail(val throwable: Throwable) : Try<Nothing>()
    data class Success<T>(val data : T) : Try<T>()

    companion object{
        operator fun <T> invoke(f : () -> T) : Try<T> = try { Success(f()) } catch (e : Throwable) { Fail(e)
        }
    }

    fun isSuccess() : Boolean = this is Success
    fun isFail() : Boolean = this is Fail

    fun getOrThrow() : T = when(this){
        is Success -> data
        is Fail -> throw throwable
    }

    inline fun <R> map(crossinline f : (T) -> R) : Try<R> = when(this){
        is Fail -> this
        is Success -> Try{f(data)}
    }

    inline fun <R> fMap(crossinline f : (T) -> Try<R>) : Try<R> = when(this){
        is Fail -> this
        is Success -> Try{f(data).getOrThrow()}
    }

    override fun equals(other: Any?): Boolean {
        return when{
            other !is Try<*> -> false
            other is Fail && this is Fail -> throwable == other.throwable
            other is Success<*> && this is Success -> other.data == data
            else -> false
        }
    }

    override fun hashCode(): Int = when(this){
        is Fail -> throwable.hashCode()
        is Success -> data.hashCode()
    }
}

fun <T> Try<T>.get() : Option<T> = when(this){
    is Try.Success -> Option.Some(data)
    else -> Option.None
}

inline fun <T> Throwable.asTry() : Try<T> = Try.Fail(this)