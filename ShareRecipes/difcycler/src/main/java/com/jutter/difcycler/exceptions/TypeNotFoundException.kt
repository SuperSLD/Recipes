package com.jutter.difcycler.exceptions

/**
 * Ошибка при создании холдера.
 */
class TypeNotFoundException: Exception() {
    override val message = "view holder type not found, check adapter initialisation"
}