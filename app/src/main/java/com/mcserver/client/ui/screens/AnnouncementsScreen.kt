package com.mcserver.client.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mcserver.client.R
import com.mcserver.client.data.model.Announcement
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementsScreen() {
    var announcements by remember { mutableStateOf(sampleAnnouncements) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.announcements_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        if (announcements.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Announcement,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.announcements_no_data),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(announcements) { announcement ->
                    AnnouncementCard(announcement = announcement)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementCard(announcement: Announcement) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (announcement.isImportant) 
                MaterialTheme.colorScheme.errorContainer 
            else 
                MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (announcement.isImportant) {
                        Icon(
                            imageVector = Icons.Default.PriorityHigh,
                            contentDescription = stringResource(R.string.announcements_important),
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = announcement.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (announcement.isImportant) 
                            MaterialTheme.colorScheme.onErrorContainer 
                        else 
                            MaterialTheme.colorScheme.onSurface
                    )
                }
                
                Text(
                    text = SimpleDateFormat("MM-dd", Locale.getDefault()).format(announcement.date),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (announcement.isImportant) 
                        MaterialTheme.colorScheme.onErrorContainer 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = announcement.content,
                style = MaterialTheme.typography.bodyMedium,
                color = if (announcement.isImportant) 
                    MaterialTheme.colorScheme.onErrorContainer 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = if (announcement.isExpanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis
            )
            
            if (announcement.content.length > 100) {
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = { /* TODO: 展开/收起内容 */ },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(if (announcement.isExpanded) "收起" else "展开")
                    Icon(
                        imageVector = if (announcement.isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = announcement.author,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (announcement.isImportant) 
                        MaterialTheme.colorScheme.onErrorContainer 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                if (announcement.category.isNotBlank()) {
                    AssistChip(
                        onClick = { /* TODO: 按分类筛选 */ },
                        label = { Text(announcement.category) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    )
                }
            }
        }
    }
}

// 示例数据
private val sampleAnnouncements = listOf(
    Announcement(
        id = 1,
        title = "服务器维护通知",
        content = "为了提供更好的游戏体验，我们将在本周日凌晨2点进行服务器维护，预计持续2小时。维护期间服务器将暂时关闭，请提前做好准备。维护内容包括：1. 服务器性能优化 2. 插件更新 3. 数据库清理 4. 安全补丁安装。感谢大家的理解与支持！",
        author = "技术团队",
        date = Date(),
        category = "维护",
        isImportant = true,
        isExpanded = false
    ),
    Announcement(
        id = 2,
        title = "新版本更新公告",
        content = "服务器已更新到最新版本，新增了多个生物群系和物品。请查看更新日志了解详细信息。",
        author = "管理员",
        date = Date(System.currentTimeMillis() - 86400000),
        category = "更新",
        isImportant = false,
        isExpanded = false
    ),
    Announcement(
        id = 3,
        title = "社区活动：建筑大赛",
        content = "欢迎参加我们的建筑大赛！本次比赛主题是\"未来城市\"，参赛者需要在指定区域内建造自己的作品。获奖者将获得丰厚的奖励。",
        author = "活动策划",
        date = Date(System.currentTimeMillis() - 172800000),
        category = "活动",
        isImportant = false,
        isExpanded = false
    ),
    Announcement(
        id = 4,
        title = "服务器规则更新",
        content = "为了维护良好的游戏环境，我们对服务器规则进行了更新。主要变更包括：1. 禁止恶意破坏他人建筑 2. 禁止使用外挂和作弊软件 3. 禁止恶意刷屏和辱骂他人 4. 新增举报机制。请所有玩家仔细阅读并遵守新规则。",
        author = "管理团队",
        date = Date(System.currentTimeMillis() - 259200000),
        category = "规则",
        isImportant = true,
        isExpanded = false
    )
)
