package com.leonardo.have_app.domain.entities

import kotlinx.serialization.Serializable

/**
 * A application found in the system
 *
 * @property packageName is name of this application
 * @property category is category of this application
 * @property enabled when false, indicates that all components within this application are
 * considered disabled.
 *
 */
@Serializable
open class Application(
    private val packageName: String,
    private val category: Category,
    private val enabled: Boolean,
) {
    override fun equals(other: Any?): Boolean {
        if (other is Application) {
            return (
                packageName == other.packageName &&
                category == other.category &&
                enabled == other.enabled
            )
        }

        return super.equals(other)
    }
}


@Serializable
enum class Category(val value: Int) {
    ACCESSIBILITY(8),
    AUDIO(1),
    GAME(0),
    IMAGE(3),
    MAPS(6),
    NEWS(5),
    PRODUCTIVITY(7),
    SOCIAL(4),
    UNDEFINED(-1),
}

fun getByValue(value: Int): Category {
    return Category.values().first { it.value == value }
}