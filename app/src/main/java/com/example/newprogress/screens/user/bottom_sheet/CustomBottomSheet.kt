package com.example.newprogress.screens.user.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newprogress.core_ui.components.Graph
import com.example.newprogress.core_ui.components.listDataPoint
import kotlinx.coroutines.launch


@Composable
fun UserBottomSheet() {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    ModalBottomSheetLayout(
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(127.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Swipe up to expand sheet")
            }
            Column(
                Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Graph(lines = listOf(listDataPoint()))
                Text("Sheet content")
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
                        scope.launch { scaffoldState.hide() }
                    }
                ) {
                    Text("Click to collapse sheet")
                }
            }
        },
        sheetState = scaffoldState,
    ) {
        LazyColumn() {

            items(2) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colors.primary)
                ){
                    Button(onClick = { scope.launch { scaffoldState.show() } }) {
                        Text("Click to close sheet")
                    }
                }
            }
        }
    }
}