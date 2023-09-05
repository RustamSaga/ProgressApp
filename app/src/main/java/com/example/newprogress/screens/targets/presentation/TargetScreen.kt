package com.example.newprogress.screens.targets.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TargetScreen(
    viewModel: TargetViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        val valueChange = viewModel.textState

        Text(text = valueChange.value)
        Button(onClick = { valueChange.value = " World!" }) {
            Text(text = "Button")
        }
    }
}