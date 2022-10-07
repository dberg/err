package com.cybergstudios.err

import arrow.core.Either
import arrow.core.handleErrorWith
import arrow.core.left

fun <T> (() -> T).safely(err: () -> Err): Res<T> =
    Either.catch { this() }.handleErrorWith { err().copy(throwable = it).left()  }

fun <A, B> ((A) -> B).safely(a: A, err: () -> Err): Res<B> =
    { this (a) }.safely(err)

fun <A, B, C>((A, B) -> C).safely(a: A, b: B, err: () -> Err): Res<C> =
    { this (a, b) }.safely(err)