package com.mcserver.client.data.model

import java.util.Date

data class NewsItem(
    val id: Int,
    val title: String,
    val content: String,
    val author: String,
    val date: Date,
    val isImportant: Boolean = false
)
