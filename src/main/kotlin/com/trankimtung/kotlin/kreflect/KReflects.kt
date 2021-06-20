@file:JvmName("KReflects")

package com.trankimtung.kotlin.kreflect

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

/**
 * Check if given class is written in Kotlin.
 *
 * @param type target class
 * @return true if given class is written in Kotlin.
 */
fun <T : Any> isKotlinClass(type: KClass<T>): Boolean =
    type.java.annotations.any {
        it.annotationClass.qualifiedName == "kotlin.Metadata"
    }

/**
 * Find a property in given type by name.
 *
 * @param type type to find property in.
 * @param name name of target property.
 * @return found property, null if not found.
 */
fun <T : Any> findProperty(type: KClass<T>, name: String): KProperty1<T, *>? =
    type.memberProperties.find { it.name == name }

/**
 * Find a property in given type by path.
 *
 * @param type type to find property in.
 * @param path dot-separated path to target property.
 * @return found property, null if not found.
 */
fun <T : Any> findPropertyByPath(type: KClass<T>, path: String): KProperty1<out Any, *>? {
    return if (path.contains(DOT)) {
        val property = findProperty(type, path.substringBefore(DOT)) ?: return null
        findPropertyByPath(property.returnType.jvmErasure, path.substringAfter(DOT))
    } else {
        findProperty(type, path)
    }
}

/**
 * Check if given type contains a property with specified name.
 *
 * @param type type to find property in.
 * @param name name of target property.
 * @return true if a property is found, false otherwise.
 */
fun <T : Any> containsProperty(type: KClass<T>, name: String): Boolean =
    findProperty(type, name) != null

/**
 * Check if given type contains a property at specified path.
 *
 * @param type type to find property in.
 * @param path path to target property.
 * @return true if a property is found, false otherwise.
 */
fun <T : Any> containsPropertyByPath(type: KClass<T>, path: String): Boolean =
    findPropertyByPath(type, path) != null

/**
 * Get a property's value by name.
 * Return null if property can not be found or its value is null.
 *
 * @param obj target object.
 * @param name name of target property.
 * @return property's value, can be null.
 */
fun <T : Any> getProperty(obj: T, name: String): Any? =
    findProperty(obj.javaClass.kotlin, name)?.getter?.call(obj)

/**
 * Get a property's value by path.
 * Return null if property can not be found or its value is null.
 *
 * @param obj target object.
 * @param path path to target property.
 * @return property's value, can be null.
 */
fun <T : Any> getPropertyByPath(obj: T, path: String): Any? {
    return if (path.contains(DOT)) {
        getProperty(obj, path.substringBefore(DOT))?.let {
            getPropertyByPath(it, path.substringAfter(DOT))
        }
    } else {
        getProperty(obj, path)
    }
}

private const val DOT = "."