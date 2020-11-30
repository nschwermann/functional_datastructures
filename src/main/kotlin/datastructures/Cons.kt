package datastructures

interface Cons<out CAR, out CDR>{
    val car : CAR
    val cdr : CDR

    companion object{
        operator fun <A, B> invoke(a : A, b : B) : Cons<A, B> = object : Cons<A, B> {
            override val car: A = a
            override val cdr: B = b

            override fun toString(): String {
                return "($car, $cdr)"
            }
        }

        operator fun <A, B> invoke(pair: Pair<A, B>) : Cons<A, B> = invoke(pair.first, pair.second)
    }

    operator fun component1() : CAR = car
    operator fun component2() : CDR = cdr

}

infix fun <CAR, CDR> CAR.cons(cdr : CDR) = Cons(this, cdr)