package com.example.chatter.feature.chat

data class Message(
    val id: String = "",
    val senderId: String = "",
    val messageText: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val senderName: String = "",
    val senderImageProfile: String? = null,
    val imageUrlMessage: String? = null
)