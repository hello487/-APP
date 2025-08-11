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
import androidx.compose.ui.unit.dp
import com.mcserver.client.R
import com.mcserver.client.data.model.WhitelistApplication
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhitelistScreen() {
    var applications by remember { mutableStateOf(sampleApplications) }
    var showApplyDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.whitelist_title)) },
                actions = {
                    IconButton(onClick = { showApplyDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "申请白名单")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                WhitelistInfoCard()
            }
            
            items(applications) { application ->
                WhitelistApplicationCard(application = application)
            }
        }
        
        if (showApplyDialog) {
            ApplyWhitelistDialog(
                onDismiss = { showApplyDialog = false },
                onApply = { username, reason ->
                    val newApplication = WhitelistApplication(
                        id = applications.size + 1,
                        username = username,
                        reason = reason,
                        status = WhitelistStatus.PENDING,
                        date = Date()
                    )
                    applications = listOf(newApplication) + applications
                    showApplyDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhitelistInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "白名单申请说明",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "• 请填写真实的游戏用户名\n• 申请理由需要详细说明\n• 审核时间通常为1-3个工作日\n• 通过后即可加入服务器游戏",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhitelistApplicationCard(application: WhitelistApplication) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = application.username,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                StatusChip(status = application.status)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = application.reason,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(application.date),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun StatusChip(status: WhitelistStatus) {
    val (backgroundColor, textColor, text) = when (status) {
        WhitelistStatus.PENDING -> Triple(
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.onTertiaryContainer,
            stringResource(R.string.whitelist_pending)
        )
        WhitelistStatus.APPROVED -> Triple(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer,
            stringResource(R.string.whitelist_approved)
        )
        WhitelistStatus.REJECTED -> Triple(
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.onErrorContainer,
            stringResource(R.string.whitelist_rejected)
        )
    }
    
    AssistChip(
        onClick = { },
        label = { Text(text) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = backgroundColor,
            labelColor = textColor
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplyWhitelistDialog(
    onDismiss: () -> Unit,
    onApply: (String, String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.whitelist_title)) },
        text = {
            Column {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(stringResource(R.string.whitelist_username)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = reason,
                    onValueChange = { reason = it },
                    label = { Text(stringResource(R.string.whitelist_reason)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (username.isNotBlank() && reason.isNotBlank()) {
                        onApply(username, reason)
                    }
                }
            ) {
                Text(stringResource(R.string.whitelist_submit))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

enum class WhitelistStatus {
    PENDING, APPROVED, REJECTED
}

// 示例数据
private val sampleApplications = listOf(
    WhitelistApplication(
        id = 1,
        username = "MinecraftPlayer123",
        reason = "我是一个热爱Minecraft的玩家，希望能够加入这个友好的服务器社区。我喜欢建造和探索，会遵守服务器规则。",
        status = WhitelistStatus.APPROVED,
        date = Date(System.currentTimeMillis() - 86400000)
    ),
    WhitelistApplication(
        id = 2,
        username = "BuilderPro",
        reason = "我是一名建筑爱好者，想要在服务器中展示我的建筑作品，并与其他玩家交流建筑技巧。",
        status = WhitelistStatus.PENDING,
        date = Date()
    )
)
