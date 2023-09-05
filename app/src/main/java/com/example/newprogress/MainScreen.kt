package com.example.newprogress

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newprogress.components.BottomAppNavigation
import com.example.newprogress.core.navigation.Destination
import com.example.newprogress.core.navigation.NavHost
import com.example.newprogress.core.navigation.NavigationIntent
import com.example.newprogress.core.navigation.composable
import com.example.newprogress.screens.notes.presentation.NotesScreen
import com.example.newprogress.screens.objects_of_observation.presentation.ObservationScreen
import com.example.newprogress.screens.settings.presentation.SettingsScreen
import com.example.newprogress.screens.targets.presentation.TargetScreen
import com.example.newprogress.screens.user.presentation.UserScreen
import com.example.newprogress.ui.theme.NewProgressTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
//    val scaffoldState = rememberBottomSheetScaffoldState(
//        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Expanded)
//    )

    NavigationEffects(
        navigationChannel = viewModel.navigationChannel,
        navHostController = navController
    )
    NewProgressTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomAppNavigation(
                    navController = navController
                )
            },

        ) {
            NavHost(
                navController = navController,
                startDestination = Destination.UserScreen
            ) {
                composable(destination = Destination.UserScreen) {
                    UserScreen()
                }
                composable(destination = Destination.ObservationScreen) {
                    ObservationScreen()
                }
                composable(destination = Destination.TargetScreen) {
                    TargetScreen()
                }
                composable(destination = Destination.NotesScreen) {
                    NotesScreen()
                }
                composable(destination = Destination.SettingsScreen) {
                    SettingsScreen()
                }
            }
        }
    }
}


@Composable
fun NavigationEffects(
    navigationChannel: Channel<NavigationIntent>,
    navHostController: NavHostController
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }
                is NavigationIntent.NavigateTo -> {
                    navHostController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }
            }
        }
    }
}