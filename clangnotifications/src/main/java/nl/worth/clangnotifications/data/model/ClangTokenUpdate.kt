package nl.worth.clangnotifications.data.model

import androidx.annotation.Keep


/**
 * INSERT CLASS DESCRIPTION HERE
 *
 * @property id PROPERTY DESCRIPTION GOES HERE
 * @property newToken PROPERTY DESCRIPTION GOES HERE
 */
@Keep
data class ClangTokenUpdate(val id: String, val newToken: String)