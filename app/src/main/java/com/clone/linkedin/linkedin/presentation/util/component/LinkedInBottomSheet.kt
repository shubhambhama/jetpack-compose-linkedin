package com.clone.linkedin.linkedin.presentation.util.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clone.linkedin.R
import com.clone.linkedin.linkedin.domain.model.BottomSheetData
import com.clone.linkedin.ui.theme.textIconViewColor
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LinkedInBottomSheet(menuItems: List<BottomSheetData>, onDismiss: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()

    BackHandler(sheetState.isExpanded) {
        scope.launch {
            sheetState.collapse()
            onDismiss.invoke()
        }
    }

    LaunchedEffect(Unit) {
        scope.launch {
            sheetState.apply {
                if (isExpanded) {
                    collapse()
                    onDismiss.invoke()
                } else expand()
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            androidx.compose.material3.Surface(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(Color.Transparent)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.Transparent)
                        .pointerInput(Unit) {
                            detectDragGestures { _, dragAmount ->
                                if (dragAmount.y > 0) {
                                    scope.launch {
                                        sheetState.collapse()
                                        onDismiss.invoke()
                                    }
                                }
                            }
                        }
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth()
                                .wrapContentHeight().clickable(interactionSource = interactionSource, indication = null) {
                                    scope.launch {
                                        sheetState.collapse()
                                        onDismiss.invoke()
                                    }
                                },
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Divider(
                                color = textIconViewColor(),
                                thickness = 2.dp,
                                modifier = Modifier
                                    .width(54.dp)
                                    .height(6.dp)
                                    .clip(CircleShape)
                            )
                        }

                        SheetList(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp), menuItems)
                    }
                }
            }
        },
        sheetBackgroundColor = Color.Transparent,
        backgroundColor = Color.Transparent,
        sheetPeekHeight = 0.dp
    ) {
        Box {  }
    }
}

@Deprecated("Using Bottom Sheet using BottomSheetScaffold.")
@ExperimentalMaterialApi
@Composable
fun LinkedInBottomSheet2(onDismiss: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
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
            androidx.compose.material3.Surface(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(Color.Transparent)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.Transparent)
                        .pointerInput(Unit) {
                            detectVerticalDragGestures { change, dragAmount ->
                                change.consume()
                                when {
                                    dragAmount > 0 -> {
                                        scope.launch {
                                            sheetState.apply {
                                                if (isVisible) {
                                                    hide()
                                                    onDismiss.invoke()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        },
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth()
                                .wrapContentHeight().clickable(interactionSource = interactionSource, indication = null) { onDismiss.invoke() },
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Divider(
                                color = textIconViewColor(),
                                thickness = 2.dp,
                                modifier = Modifier
                                    .width(54.dp)
                                    .height(6.dp)
                                    .clip(CircleShape)
                            )
                        }

                        SheetList(
                            modifier = Modifier.padding(horizontal = 16.dp), listOf(
                                BottomSheetData(R.drawable.ic_jobs, "Save"),
                                BottomSheetData(R.drawable.ic_jobs, "Share via"),
                                BottomSheetData(R.drawable.ic_jobs, "I don't want to see this"),
                                BottomSheetData(R.drawable.ic_jobs, "Unfollow"),
                                BottomSheetData(R.drawable.ic_jobs, "Remove connection"),
                                BottomSheetData(R.drawable.ic_jobs, "Report post"),
                            )
                        )
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {

    }
}

@Composable
private fun SheetList(modifier: Modifier = Modifier, listData: List<BottomSheetData>) {
    LazyColumn(modifier = Modifier.padding(vertical = 16.dp), userScrollEnabled = false) {
        items(listData.size) {
            SheetItems(modifier.padding(vertical = 8.dp), listData[it])
        }
    }
}

@Composable
private fun SheetItems(modifier: Modifier = Modifier, data: BottomSheetData) {
    Row(modifier) {
        Row(Modifier.weight(9f)) {
            Image(
                painter = painterResource(data.resId), modifier = Modifier.size(22.dp), contentDescription = data.value, colorFilter = ColorFilter.tint(
                    textIconViewColor().copy(alpha = ContentAlpha.medium)
                )
            )
            Spacer(Modifier.width(16.dp))
            Text(text = data.value, fontSize = 14.sp, color = textIconViewColor())
        }
        data.arrowResId?.let {
            Image(
                painter = painterResource(data.arrowResId), modifier = Modifier.size(24.dp).weight(1f), contentDescription = data.value,
                colorFilter = ColorFilter.tint(textIconViewColor())
            )
        }
    }
}