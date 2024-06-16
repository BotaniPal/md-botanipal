package com.botanipal.botanipal.data.model

import java.sql.Timestamp

data class Topics(
    val title: String,
    val description: String,
    val timestamp: Timestamp,
    val isBookmarked: Boolean
)
