package com.example.newprogress.core_ui.calendar


import android.widget.TimePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerScreen() {

    val dateTime = LocalDateTime.now()

    val timePickerState = remember {
        TimePickerState(
            is24Hour = true,
            initialHour = dateTime.hour,
            initialMinute = dateTime.minute
        )
    }

    TimePicker(state = timePickerState)
}