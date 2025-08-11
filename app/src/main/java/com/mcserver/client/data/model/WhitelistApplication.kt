package com.mcserver.client.data.model

import java.util.Date

data class WhitelistApplication(
    val id: Int,
    val username: String,
    val reason: String,
    val status: WhitelistStatus,
    val date: Date
)
