package com.mcserver.client.data.model

import java.util.Date

data class ForumPost(
    val id: Int,
    val title: String,
    val content: String,
    val author: String,
    val date: Date,
    val replies: Int,
    val likes: Int
)
