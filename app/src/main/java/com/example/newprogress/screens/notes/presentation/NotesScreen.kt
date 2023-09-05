package com.example.newprogress.screens.notes.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign


@Composable
fun NotesScreen() {

    Text(
        modifier = Modifier.fillMaxSize().wrapContentHeight(),
        text = "NotesScreen",
        textAlign = TextAlign.Center
    )
}