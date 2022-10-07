package com.cybergstudios.err.test

import arrow.core.identity
import com.cybergstudios.err.Err
import com.cybergstudios.err.Res

fun <T> Res<T>.assertRight(): T =
    this.fold(
        { throw Exception("Expected Right but got Left($this) ") },
        { it }
    )

fun <T> Res<T>.assertLeft(): Err =
    this.fold(::identity) {
        throw Exception("Expected Left but got Right($it)")
    }

fun <T> T?.assertNotNull(): T =
    this ?: throw Exception("Expected non-null value")