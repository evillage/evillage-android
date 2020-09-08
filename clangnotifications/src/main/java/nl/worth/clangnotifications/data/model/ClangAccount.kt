package nl.worth.clangnotifications.data.model

import androidx.annotation.Keep

/**
 * INSERT CLASS DESCRIPTION HERE
 *
 * @property token PROPERTY DESCRIPTION GOES HERE
 * @property deviceId Unique device identifier
 * @property integrationId PROPERTY DESCRIPTION GOES HERE
 */
@Keep
data class ClangAccount (
    val token: String,
    val deviceId: String,
    val integrationId: String
)