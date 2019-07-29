package nl.worth.clangnotifications.data.model

data class NotificationResponse(val success: Boolean, val errors: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NotificationResponse

        if (success != other.success) return false
        if (!errors.contentEquals(other.errors)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = success.hashCode()
        result = 31 * result + errors.contentHashCode()
        return result
    }
}
