package nl.worth.clangnotifications.data.model


/**
 * INSERT CLASS DESCRIPTION HERE
 *
 * @property userId Unique user identifier
 * @property event PROPERTY DESCRIPTION GOES HERE
 * @property data PROPERTY DESCRIPTION GOES HERE
 * @property integrationId PROPERTY DESCRIPTION GOES HERE
 */
data class ClangEvent(
    val userId: String,
    val event: String,
    val data: Map<String, String>,
    val integrationId: String
)