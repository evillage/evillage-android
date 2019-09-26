package nl.worth.clangnotifications.data.model

data class EventLogRequest(val userId: String, val event: String, val data: Map<String, String>)