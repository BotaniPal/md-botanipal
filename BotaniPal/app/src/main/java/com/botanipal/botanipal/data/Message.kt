package com.botanipal.botanipal.data

data class Message(
    val text: String? = null,
    val name: String? = null,
    val photoUrl: String? = null,
    val timestamp: Long? = null,
    val isBookmarked: Boolean? = null
)
