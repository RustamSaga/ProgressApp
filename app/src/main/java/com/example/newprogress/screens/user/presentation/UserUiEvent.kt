package com.example.newprogress.screens.user.presentation

import com.example.newprogress.core.domain.models.ProgressTargetModel
import java.time.OffsetDateTime

sealed class UserUiEvent {
    data class Navigate(val route: String): UserUiEvent()
    object NavigateUp: UserUiEvent()
//    data class ShowSnackBar(val massage: UiText): UserUiEvent()
    class ChooseADay(private val data: OffsetDateTime): UserUiEvent()
    class ChooseATarget(private val target: ProgressTargetModel): UserUiEvent()
}