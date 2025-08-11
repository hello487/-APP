package com.mcserver.client.data.api

import com.mcserver.client.data.model.NewsItem
import com.mcserver.client.data.model.ForumPost
import com.mcserver.client.data.model.Announcement
import com.mcserver.client.data.model.WhitelistApplication
import retrofit2.http.*

interface ApiService {
    
    // 新闻相关API
    @GET("news")
    suspend fun getNews(): List<NewsItem>
    
    @GET("news/{id}")
    suspend fun getNewsDetail(@Path("id") id: Int): NewsItem
    
    // 论坛相关API
    @GET("forum/posts")
    suspend fun getForumPosts(): List<ForumPost>
    
    @POST("forum/posts")
    suspend fun createForumPost(@Body post: ForumPost): ForumPost
    
    @GET("forum/posts/{id}")
    suspend fun getForumPostDetail(@Path("id") id: Int): ForumPost
    
    // 公告相关API
    @GET("announcements")
    suspend fun getAnnouncements(): List<Announcement>
    
    @GET("announcements/{id}")
    suspend fun getAnnouncementDetail(@Path("id") id: Int): Announcement
    
    // 白名单相关API
    @GET("whitelist/applications")
    suspend fun getWhitelistApplications(): List<WhitelistApplication>
    
    @POST("whitelist/applications")
    suspend fun submitWhitelistApplication(@Body application: WhitelistApplication): WhitelistApplication
    
    @GET("whitelist/applications/{id}")
    suspend fun getWhitelistApplicationDetail(@Path("id") id: Int): WhitelistApplication
    
    // 用户相关API
    @GET("user/profile")
    suspend fun getUserProfile(): UserProfile
    
    @PUT("user/profile")
    suspend fun updateUserProfile(@Body profile: UserProfile): UserProfile
    
    // 认证相关API
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
    
    @POST("auth/logout")
    suspend fun logout(): LogoutResponse
}

// 数据模型
data class UserProfile(
    val id: Int,
    val username: String,
    val email: String,
    val joinDate: String,
    val gameStats: GameStats
)

data class GameStats(
    val playTime: Int, // 小时
    val buildCount: Int,
    val rating: Double
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val user: UserProfile
)

data class LogoutResponse(
    val success: Boolean
)
