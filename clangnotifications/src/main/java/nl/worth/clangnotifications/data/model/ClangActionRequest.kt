package nl.worth.clangnotifications.data.model

data class ClangActionRequest(val notificationId: String, val userId: String, val actionId: String) {
    val event: String = "Notification"
}