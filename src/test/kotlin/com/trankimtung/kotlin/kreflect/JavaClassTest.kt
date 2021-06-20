package com.trankimtung.kotlin.kreflect

import com.trankimtung.kotlin.kreflect.test.JavaTestClass
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class JavaClassTest {

    @Test
    fun `Is not Kotlin class`() {
        assertFalse(isKotlinClass(JavaTestClass::class))
    }
}