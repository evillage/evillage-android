package nl.worth.clangnotifications.data.model

import androidx.annotation.Keep


/**
 * INSERT CLASS DESCRIPTION HERE
 *
 * @property userId Unique user identifier
 * @property integrationId PROPERTY DESCRIPTION GOES HERE
 * @property data PROPERTY DESCRIPTION GOES HERE
 */
@Keep
data class ClangProperties(val userId: String, val integrationId: String, val data: Map<String, String>)