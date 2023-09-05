package com.example.newprogress.screens.user.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.newprogress.R
import com.example.newprogress.core.domain.models.ObjectOfObservationModel
import com.example.newprogress.core.navigation.AppNavigator
import com.example.newprogress.core.utils.ManageResources
import com.example.newprogress.screens.user.domain.use_cases.UserInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val interactor: UserInteractor,
    private val appNavigator: AppNavigator,
    private val managerResources: ManageResources
) : ViewModel() {

    var user by mutableStateOf<ObjectOfObservationModel>(defaultUser())
        private set

    fun onUserEvent(event: UserEvent) {
        when(event) {
            is UserEvent.AddTarget -> {}
            is UserEvent.AddNoteOfTarget -> {}
            is UserEvent.AddNoteOfUser -> {}
            is UserEvent.AddCurrentProgress -> {}
            is UserEvent.AddStandardProgress -> {}
            is UserEvent.DeleteTarget -> {}
            is UserEvent.DeleteNoteOfUser -> {}
            is UserEvent.DeleteNoteOfTarget -> {}
            is UserEvent.DeleteStandardProgress -> {}
            is UserEvent.DeleteCurrentProgress -> {}
            is UserEvent.UpdateTarget -> {}
            is UserEvent.UpdateNoteOfUser -> {}
            is UserEvent.UpdateNoteOfTarget -> {}
            is UserEvent.UpdateCurrentProgress -> {}
            is UserEvent.UpdateStandardProgress -> {}
        }
    }

    fun onUiEvent(event: UserUiEvent) {
        when(event) {
            is UserUiEvent.ChooseADay -> {}
            is UserUiEvent.ChooseATarget -> {}
            is UserUiEvent.Navigate -> {}
            is UserUiEvent.NavigateUp -> {}
        }
    }

    private fun defaultUser() = ObjectOfObservationModel(
        id = 1,
        firstName = managerResources.string(R.string.progressor),
        lastName = "",
        description = "",
        observed = false,
        checkInTime = OffsetDateTime.now(),
        isActive = true,
        targets = emptyList(),
        notes = emptyList()
    )

}