package com.example.newprogress.screens.user.presentation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newprogress.core_ui.calendar.DatePickerScreen


@Composable
fun UserScreen(
    userViewModel: UserViewModel = hiltViewModel()
) {
    DatePickerScreen()
}

