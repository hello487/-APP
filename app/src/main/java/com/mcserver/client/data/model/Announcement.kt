package com.mcserver.client.data.model

import java.util.Date

data class Announcement(
    val id: Int,
    val title: String,
    val content: String,
    val author: String,
    val date: Date,
    val category: String,
    val isImportant: Boolean = false,
    val isExpanded: Boolean = false
)
