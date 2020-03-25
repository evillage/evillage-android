package nl.worth.clangnotifications.data.model


/**
 * INSERT CLASS DESCRIPTION HERE
 *
 * @property userId Unique user identifier
 * @property integrationId PROPERTY DESCRIPTION GOES HERE
 * @property data PROPERTY DESCRIPTION GOES HERE
 */
data class ClangProperties(val userId: String, val integrationId: String, val data: Map<String, String>)