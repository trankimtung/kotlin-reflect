package com.trankimtung.kotlin.kreflect

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class KotlinClassTest {

    @Test
    fun `Is Kotlin class`() {
        assertTrue(isKotlinClass(TestClass::class))
    }

    @Test
    fun `Not null property exists at top level`() {
        assertTrue(containsProperty(TestClass::class, "prop1"))
        assertTrue(containsPropertyByPath(TestClass::class, "prop1"))
        assertEquals("prop1", findProperty(TestClass::class, "prop1")?.name)
        assertEquals("prop1", findPropertyByPath(TestClass::class, "prop1")?.name)

        val obj = TestClass()
        assertEquals("prop1", getProperty(obj, "prop1"))
        assertEquals("prop1", getPropertyByPath(obj, "prop1"))
    }

    @Test
    fun `Null property exists at top level`() {
        assertTrue(containsProperty(TestClass::class, "prop2"))
        assertTrue(containsPropertyByPath(TestClass::class, "prop2"))
        assertEquals("prop2", findProperty(TestClass::class, "prop2")?.name)
        assertEquals("prop2", findPropertyByPath(TestClass::class, "prop2")?.name)

        val obj = TestClass()
        assertNull(getProperty(obj, "prop2"))
        assertNull(getPropertyByPath(obj, "prop2"))
    }


    @Test
    fun `Not null property exists at lower level`() {
        assertFalse(containsProperty(TestClass::class, "nested.prop1"))
        assertTrue(containsPropertyByPath(TestClass::class, "nested.prop1"))
        assertNull(findProperty(TestClass::class, "nested.prop1"))
        assertEquals("prop1", findPropertyByPath(TestClass::class, "nested.prop1")?.name)

        val obj = TestClass(nested = TestClass())
        assertNull(getProperty(obj, "nested.prop1"))
        assertEquals("prop1", getPropertyByPath(obj, "nested.prop1"))
    }

    @Test
    fun `Null property exists at lower level`() {
        assertFalse(containsProperty(TestClass::class, "nested.prop2"))
        assertTrue(containsPropertyByPath(TestClass::class, "nested.prop2"))
        assertNull(findProperty(TestClass::class, "nested.prop2"))
        assertEquals("prop2", findPropertyByPath(TestClass::class, "nested.prop2")?.name)

        val obj = TestClass(nested = TestClass())
        assertNull(getProperty(obj, "nested.prop2"))
        assertNull(getPropertyByPath(obj, "nested.prop2"))
    }

    @Test
    fun `Property does not exist at top level`() {
        assertFalse(containsProperty(TestClass::class, "prop3"))
        assertFalse(containsPropertyByPath(TestClass::class, "prop3"))
        assertNull(findProperty(TestClass::class, "prop3"))
        assertNull(findPropertyByPath(TestClass::class, "prop3"))

        val obj = TestClass()
        assertNull(getProperty(obj, "prop3"))
        assertNull(getPropertyByPath(obj, "prop3"))
    }

    @Test
    fun `Property does not exist at lower level`() {
        assertFalse(containsProperty(TestClass::class, "nested.prop3"))
        assertFalse(containsPropertyByPath(TestClass::class, "nested.prop3"))
        assertNull(findProperty(TestClass::class, "nested.prop3"))
        assertNull(findPropertyByPath(TestClass::class, "nested.prop3"))

        val obj = TestClass(nested = TestClass())
        assertNull(getProperty(obj, "nested.prop3"))
        assertNull(getPropertyByPath(obj, "nested.prop3"))
    }

    @Test
    fun `Null property at middle path`() {
        assertFalse(containsProperty(TestClass::class, "nested.nested.prop1"))
        assertTrue(containsPropertyByPath(TestClass::class, "nested.nested.prop1"))
        assertNull(findProperty(TestClass::class, "nested.nested.prop1"))
        assertEquals("prop1", findPropertyByPath(TestClass::class, "nested.nested.prop1")?.name)

        val obj = TestClass()
        assertNull(getProperty(obj, "nested.nested.prop1"))
        assertNull(getPropertyByPath(obj, "nested.nested.prop1"))
    }


    @Suppress("unused")
    class TestClass(
        val prop1: String = "prop1",
        val prop2: Int? = null,
        val nested: TestClass? = null
    )
}