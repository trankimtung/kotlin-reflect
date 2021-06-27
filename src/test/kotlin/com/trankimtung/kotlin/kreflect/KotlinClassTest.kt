package com.trankimtung.kotlin.kreflect

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

class KotlinClassTest : BaseTest<KotlinClassTest.TestClass>() {

    override val type: KClass<TestClass> = TestClass::class

    override fun obj(): TestClass = TestClass(nested = TestClass())

    @Test
    fun `Is Kotlin class`() {
        assertTrue(isKotlinClass(type::class))
    }

    @Suppress("unused")
    class TestClass(
        val prop1: String = "prop1",
        var prop2: Int? = null,
        val nested: TestClass? = null
    )
}