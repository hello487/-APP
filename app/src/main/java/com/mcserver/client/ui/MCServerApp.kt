package com.mcserver.client.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mcserver.client.R
import com.mcserver.client.ui.screens.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MCServerApp(
    onThemeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                val items = listOf(
                    NavigationItem(
                        route = "news",
                        title = stringResource(R.string.nav_news),
                        icon = Icons.Default.Article
                    ),
                    NavigationItem(
                        route = "forum",
                        title = stringResource(R.string.nav_forum),
                        icon = Icons.Default.Forum
                    ),
                    NavigationItem(
                        route = "announcements",
                        title = stringResource(R.string.nav_announcements),
                        icon = Icons.Default.Announcement
                    ),
                    NavigationItem(
                        route = "whitelist",
                        title = stringResource(R.string.nav_whitelist),
                        icon = Icons.Default.PersonAdd
                    ),
                    NavigationItem(
                        route = "profile",
                        title = stringResource(R.string.nav_profile),
                        icon = Icons.Default.Person
                    )
                )
                
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "news",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("news") {
                NewsScreen()
            }
            composable("forum") {
                ForumScreen()
            }
            composable("announcements") {
                AnnouncementsScreen()
            }
            composable("whitelist") {
                WhitelistScreen()
            }
            composable("profile") {
                ProfileScreen(onThemeChange = onThemeChange)
            }
        }
    }
}

data class NavigationItem(
    val route: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
