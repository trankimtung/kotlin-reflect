package com.trankimtung.kotlin.kreflect

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.random.nextLong
import kotlin.reflect.KClass

abstract class BaseTest<T : Any> {

    abstract val type: KClass<T>

    abstract fun obj(): T

    @Test
    fun `Not null property exists at top level`() {
        assertTrue(containsProperty(type, "prop1"))
        assertTrue(containsPropertyByPath(type, "prop1"))
        assertEquals("prop1", findProperty(type, "prop1")?.name)
        assertEquals("prop1", findPropertyByPath(type, "prop1")?.name)

        val obj = obj()
        assertEquals("prop1", getProperty(obj, "prop1"))
        assertEquals("prop1", getPropertyByPath(obj, "prop1"))
    }

    @Test
    fun `Null property exists at top level`() {
        assertTrue(containsProperty(type, "prop2"))
        assertTrue(containsPropertyByPath(type, "prop2"))
        assertEquals("prop2", findProperty(type, "prop2")?.name)
        assertEquals("prop2", findPropertyByPath(type, "prop2")?.name)

        val obj = obj()
        assertNull(getProperty(obj, "prop2"))
        assertNull(getPropertyByPath(obj, "prop2"))
    }


    @Test
    fun `Not null property exists at lower level`() {
        assertFalse(containsProperty(type, "nested.prop1"))
        assertTrue(containsPropertyByPath(type, "nested.prop1"))
        assertNull(findProperty(type, "nested.prop1"))
        assertEquals("prop1", findPropertyByPath(type, "nested.prop1")?.name)

        val obj = obj()
        assertNull(getProperty(obj, "nested.prop1"))
        assertEquals("prop1", getPropertyByPath(obj, "nested.prop1"))
    }

    @Test
    fun `Null property exists at lower level`() {
        assertFalse(containsProperty(type, "nested.prop2"))
        assertTrue(containsPropertyByPath(type, "nested.prop2"))
        assertNull(findProperty(type, "nested.prop2"))
        assertEquals("prop2", findPropertyByPath(type, "nested.prop2")?.name)

        val obj = obj()
        assertNull(getProperty(obj, "nested.prop2"))
        assertNull(getPropertyByPath(obj, "nested.prop2"))
    }

    @Test
    fun `Property does not exist at top level`() {
        assertFalse(containsProperty(type, "prop3"))
        assertFalse(containsPropertyByPath(type, "prop3"))
        assertNull(findProperty(type, "prop3"))
        assertNull(findPropertyByPath(type, "prop3"))

        val obj = obj()
        assertNull(getProperty(obj, "prop3"))
        assertNull(getPropertyByPath(obj, "prop3"))
    }

    @Test
    fun `Property does not exist at lower level`() {
        assertFalse(containsProperty(type, "nested.prop3"))
        assertFalse(containsPropertyByPath(type, "nested.prop3"))
        assertNull(findProperty(type, "nested.prop3"))
        assertNull(findPropertyByPath(type, "nested.prop3"))

        val obj = obj()
        assertNull(getProperty(obj, "nested.prop3"))
        assertNull(getPropertyByPath(obj, "nested.prop3"))
    }

    @Test
    fun `Null property at middle path`() {
        assertFalse(containsProperty(type, "nested.nested.prop1"))
        assertTrue(containsPropertyByPath(type, "nested.nested.prop1"))
        assertNull(findProperty(type, "nested.nested.prop1"))
        assertEquals("prop1", findPropertyByPath(type, "nested.nested.prop1")?.name)

        val obj = obj()
        assertNull(getProperty(obj, "nested.nested.prop1"))
        assertNull(getPropertyByPath(obj, "nested.nested.prop1"))
    }

    @Test
    fun `Target property to set is immutable`() {
        val obj = obj()
        assertDoesNotThrow {
            val value = getProperty(obj, "prop1")
            setProperty(obj, "prop1", "prop1's value")
            assertEquals(value, getProperty(obj, "prop1"))
        }
    }

    @Test
    fun `Target property to set is mutable`() {
        val obj = obj()
        assertDoesNotThrow {
            val value = Random(System.currentTimeMillis()).nextInt()
            setProperty(obj, "prop2", value)
            assertEquals(value, getProperty(obj, "prop2"))
        }
    }
}