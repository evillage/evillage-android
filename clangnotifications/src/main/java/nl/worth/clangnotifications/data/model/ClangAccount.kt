package nl.worth.clangnotifications.data.model

/**
 * INSERT CLASS DESCRIPTION HERE
 *
 * @property token PROPERTY DESCRIPTION GOES HERE
 * @property deviceId Unique device identifier
 * @property integrationId PROPERTY DESCRIPTION GOES HERE
 */
data class ClangAccount (
    val token: String,
    val deviceId: String,
    val integrationId: String
)