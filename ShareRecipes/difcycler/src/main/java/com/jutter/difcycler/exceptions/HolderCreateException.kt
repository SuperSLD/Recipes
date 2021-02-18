package com.jutter.difcycler.exceptions

/**
 * Ошибка при создании холдера.
 */
class HolderCreateException(message: String): Exception() {
    override val message = message
}