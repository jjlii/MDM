package com.example.core

sealed class Either<out L, out R> {

    data class Failure<out L>(val l: L) : Either<L, Nothing>()
    data class Sucess<out R>(val r: R) : Either<Nothing, R>()

    val isLeft get() = this is Failure<L>
    val isRight get() = this is Sucess<R>

    fun fold(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Failure -> fnL(l)
            is Sucess -> fnR(r)
        }
}