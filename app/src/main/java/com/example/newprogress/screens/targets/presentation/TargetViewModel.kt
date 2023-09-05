package com.example.newprogress.screens.targets.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.newprogress.core.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TargetViewModel @Inject constructor(
    appNavigator: AppNavigator
): ViewModel() {

    val textState = mutableStateOf("")


}