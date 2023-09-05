package com.example.newprogress.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.newprogress.core.navigation.RoutesScreens

sealed class ButtonItem(val title: String, val icon: ImageVector, val route: String) {
    object UserScreenBtn: ButtonItem("User", Icons.Default.Person, RoutesScreens.ROUTE_USER_SCREEN)
    object ObservationScreenBtn: ButtonItem("Observation", Icons.Default.Group, RoutesScreens.ROUTE_OBSERVATION_SCREEN)
    object TargetScreenBtn: ButtonItem("Target", Icons.Default.Add, RoutesScreens.ROUTE_TARGET_SCREEN)
    object NotesScreenBtn: ButtonItem("Note", Icons.Default.Note, RoutesScreens.ROUTE_NOTES_SCREEN)
    object SettingsScreenBtn: ButtonItem("Setting", Icons.Default.Settings, RoutesScreens.ROUTE_SETTINGS_SCREEN)
}