package nl.worth.clangnotifications.data.model

import androidx.annotation.Keep

/**
 * INSERT CLASS DESCRIPTION HERE
 *
 * @property id PROPERTY DESCRIPTION GOES HERE
 * @property secret PROPERTY DESCRIPTION GOES HERE
 */
@Keep
data class ClangAccountResponse(val id: String, val secret: String)