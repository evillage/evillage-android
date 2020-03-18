package nl.worth.clangnotifications.data.model

data class PropertiesRequest(val userId: String, val integrationId: String, val data: Map<String, String>)