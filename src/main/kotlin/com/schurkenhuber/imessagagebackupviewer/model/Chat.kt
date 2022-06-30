package com.schurkenhuber.imessagagebackupviewer.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

data class Chat(
    @JsonProperty("chat_id") val chatId: Long,
    @JsonProperty("chat_identifier") val chatIdentifier: String,
    @JsonProperty("display_name") val displayName: String,
    @JsonProperty("last_message_date") val lastMessageDate: Long,
    @JsonProperty("participants") val participants: Map<String, String>,
    @JsonProperty val messages: List<Message>,
)

data class Message(
    @JsonProperty val id: Long,
    @JsonProperty val date: Long,
    @JsonProperty val sender: String,
    @JsonProperty("is_from_me") val isFromMe: Boolean,
    @JsonProperty val text: String,
    @JsonProperty("attachment_path") val attachmentPath: String?,
) {
    val dateTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this.date), ZoneId.of("UTC"))
}
