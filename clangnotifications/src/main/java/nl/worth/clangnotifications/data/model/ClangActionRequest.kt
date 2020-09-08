package nl.worth.clangnotifications.data.model

import androidx.annotation.Keep

/**
 * INSERT CLASS DESCRIPTION HERE
 *
 * @property notificationId PROPERTY DESCRIPTION GOES HERE
 * @property userId Unique user identifier
 * @property actionId PROPERTY DESCRIPTION GOES HERE
 * @property event PROPERTY DESCRIPTION GOES HERE
 */
@Keep
data class ClangActionRequest(val notificationId: String, val userId: String, val actionId: String, val event: String = "Notification")