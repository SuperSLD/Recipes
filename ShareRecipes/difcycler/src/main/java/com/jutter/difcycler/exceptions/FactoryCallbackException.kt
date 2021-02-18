package com.jutter.difcycler.exceptions

/**
 * Ошибка при создании холдера, без калбеков.
 */
class FactoryCallbackException: Exception() {
    override val message = "callback not initialise"
}