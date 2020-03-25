package nl.worth.clangnotifications.data.model

/**
 * INSERT CLASS DESCRIPTION HERE
 *
 * @property notificationId PROPERTY DESCRIPTION GOES HERE
 * @property userId Unique user identifier
 * @property actionId PROPERTY DESCRIPTION GOES HERE
 * @property event PROPERTY DESCRIPTION GOES HERE
 */
internal data class ClangActionRequest(val notificationId: String, val userId: String, val actionId: String, val event: String = "Notification")