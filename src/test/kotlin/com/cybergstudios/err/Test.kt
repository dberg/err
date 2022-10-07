package com.cybergstudios.err

import com.cybergstudios.err.test.assertLeft
import com.cybergstudios.err.test.assertNotNull
import org.junit.jupiter.api.Test
import kotlin.test.assertContains
import kotlin.test.assertSame

enum class MyErrId : ErrId {
    UserServiceFailure
}

data class User(val name: String)

class Test {
    @Test
    fun `assert missiles are under control`() {
        val err = loadUser("jdoe").assertLeft()
        assertSame(MyErrId.UserServiceFailure, err.id)
        val msg = err.throwable?.message.assertNotNull()
        assertContains(msg, "Network failure")
    }

    private fun loadUser(username: String): Res<User> =
        ::loadUserUnsafeAsSeenInTheWild.safely(username) {
            Err(MyErrId.UserServiceFailure, "Unsafe function failed")
        }

    private fun loadUserUnsafeAsSeenInTheWild(username: String): User {
        throw Exception("Network failure fetching $username")
    }
}