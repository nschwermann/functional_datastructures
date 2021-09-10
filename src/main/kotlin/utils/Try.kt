package utils

sealed class Try<out T> {

    data class Fail(val throwable: Throwable) : Try<Nothing>()
    data class Success<T>(val data : T) : Try<T>()

    companion object{
        inline operator fun <T> invoke(f : () -> T) : Try<T> = try { Success(f()) } catch (e : Throwable) { Fail(e) }
    }

    fun isSuccess() : Boolean = this is Success
    fun isFail() : Boolean = this is Fail

    fun getOrThrow() : T = when(this){
        is Success -> data
        is Fail -> throw throwable
    }

    fun getOrDefault(default : @UnsafeVariance T) : T = when(this) {
        is Success -> data
        is Fail -> default
    }

    inline fun <R> map(f : (T) -> R) : Try<R> = try {
        when(this){
            is Success -> Success(f(data))
            is Fail -> this
        }

    }catch (t : Throwable){
        t.asTry()
    }

    inline fun <R> fMap(f : (T) -> Try<R>) : Try<R> = try {
        val next : Try<R> = when(this){
            is Success -> f(data)
            is Fail -> this
        }
        next
    }catch (t : Throwable){
        t.asTry()
    }

    inline fun onSuccess(ifSuccess : (T) -> Unit) : Try<T> = try{
        if(this is Success) ifSuccess(data)
        this
    }catch (t : Throwable){
        t.asTry()
    }

    inline fun onFail(ifFail : (Throwable) -> Unit) : Try<T> = try{
        when(this){
            is Success -> this
            is Fail -> {
                ifFail(throwable)
                this
            }
        }
    }catch (t : Throwable){
        t.asTry()
    }

    inline fun finally(block : () -> Unit) : Try<T> = Try {
        block()
    }.fMap { this }

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


fun <T> Throwable.asTry() : Try<T> = Try.Fail(this)

fun <T> Try<T?>.filterNull() : Try<T> = this.map { it!! }