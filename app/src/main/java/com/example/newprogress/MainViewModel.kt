package com.example.newprogress

import androidx.lifecycle.ViewModel
import com.example.newprogress.core.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appNavigator: AppNavigator
) : ViewModel() {

    val navigationChannel = appNavigator.navigationChannel

}