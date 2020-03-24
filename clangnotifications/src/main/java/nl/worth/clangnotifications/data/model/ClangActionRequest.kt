package nl.worth.clangnotifications.data.model

/**
 * INSERT CLASS DESCRIPTION HERE
 *
 * @property notificationId PROPERTY DESCRIPTION GOES HERE
 * @property userId PROPERTY DESCRIPTION GOES HERE
 * @property actionId PROPERTY DESCRIPTION GOES HERE
 * @property event PROPERTY DESCRIPTION GOES HERE
 */
data class ClangActionRequest(val notificationId: String, val userId: String, val actionId: String, val event: String = "Notification")