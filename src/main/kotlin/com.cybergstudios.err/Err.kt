package com.cybergstudios.err

import arrow.core.Either
import arrow.core.Validated
import arrow.typeclasses.Semigroup

data class Err(
    val id: ErrId,
    val msg: String,
    val throwable: Throwable? = null,
    val tail: Err? = null,
)

interface ErrId

enum class ErrIdDefault : ErrId {
    InvalidArgument
}

typealias Res<T> = Either<Err, T>

typealias ResV<T> = Validated<Err, T>

object ErrSemigroup : Semigroup<Err> {
    override fun Err.combine(b: Err): Err =
        if (tail == null) copy(tail = b)
        else copy(tail = tail.combine(b))
}

