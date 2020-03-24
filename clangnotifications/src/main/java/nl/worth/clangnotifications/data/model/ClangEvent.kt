package nl.worth.clangnotifications.data.model

data class ClangEvent(
    val userId: String,
    val event: String,
    val data: Map<String, String>,
    val integrationId: String
)