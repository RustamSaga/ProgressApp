package com.example.newprogress.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomAppNavigation(
    navController: NavController
) {
    val listItems = listOf(
        ButtonItem.UserScreenBtn,
        ButtonItem.ObservationScreenBtn,
        ButtonItem.TargetScreenBtn,
        ButtonItem.NotesScreenBtn,
        ButtonItem.SettingsScreenBtn
    )

    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background
    ) {
        listItems.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(item.route) {
                            inclusive = true
//                            saveState = true
                        }
                        launchSingleTop = true
//                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.route
                    )
                },
                label = {
                    Text(text = item.title, fontSize = 9.sp)
                },
                selectedContentColor = Color(40, 48, 59),
                unselectedContentColor = Color.Gray
            )
        }
    }


}