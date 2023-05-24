package com.clone.linkedin.linkedin.presentation.util

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun LinkedInBottomSheet(onDismiss: () -> Unit) {
    val offsetY = remember { mutableStateOf(0f) }
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, confirmValueChange = { false })
    val scope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        scope.launch {
            sheetState.hide()
            onDismiss.invoke()
        }
    }

    LaunchedEffect(Unit) {
        scope.launch {
            sheetState.apply {
                if (isVisible) {
                    hide()
                    onDismiss.invoke()
                } else show()
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            when {
                                dragAmount.y > 0 -> {
                                    scope.launch {
                                        sheetState.apply {
                                            if (isVisible) {
                                                hide()
                                                onDismiss.invoke()
                                            }
                                        }
                                    }
                                }

                                dragAmount.y < 0 -> {
                                    scope.launch {
                                        sheetState.apply {
                                            show()
                                        }
                                    }
                                }
                            }
                            offsetY.value += dragAmount.y
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Bottom sheet",
                    fontSize = 60.sp
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
    }
}