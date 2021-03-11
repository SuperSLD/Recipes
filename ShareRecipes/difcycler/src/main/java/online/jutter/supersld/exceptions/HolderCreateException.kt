package online.jutter.supersld.exceptions

/**
 * Ошибка при создании холдера.
 */
class HolderCreateException(message: String): Exception() {
    override val message = message
}