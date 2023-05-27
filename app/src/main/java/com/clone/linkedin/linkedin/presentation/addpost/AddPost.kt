package com.clone.linkedin.linkedin.presentation.addpost

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.clone.linkedin.R
import com.clone.linkedin.linkedin.presentation.util.component.ActionButton
import com.clone.linkedin.linkedin.presentation.util.component.AskForPermission
import com.clone.linkedin.linkedin.presentation.util.component.LinkedInBottomSheet
import com.clone.linkedin.linkedin.presentation.util.component.RoundImage
import com.clone.linkedin.ui.theme.DarkGray60
import com.clone.linkedin.ui.theme.LightBlue
import com.clone.linkedin.ui.theme.textIconViewColor

@Composable
fun AddPost(navController: NavController, onDismiss: () -> Unit) {
    val isPostGrayedOut = remember { mutableStateOf(true) }
    val bottomSheetVisible = remember { mutableStateOf(false) }
    val viewModel: AddPostViewModel = hiltViewModel()
    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Toobar(Modifier.padding(top = 16.dp), isPostGrayedOut) {
                    onDismiss.invoke()
                }
                AddPostTopHeader()
                WritePost(hint = "What do you want to talk about?", modifier = Modifier.fillMaxWidth(), isPostGrayedOut)
                Spacer(modifier = Modifier.weight(1f))
                Footer(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), bottomSheetVisible)
            }
            if (bottomSheetVisible.value) {
                LinkedInBottomSheet(viewModel.getDataForPostMenu()) {
                    bottomSheetVisible.value = false
                }
            }
        }
    }
}

@Composable
private fun Toobar(modifier: Modifier = Modifier, isPostGrayedOut: MutableState<Boolean>, onCloseButton: () -> Unit) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = "close",
            modifier = Modifier.size(22.dp).clickable { onCloseButton.invoke() },
            colorFilter = ColorFilter.tint(textIconViewColor())
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(R.drawable.ic_time), contentDescription = "Schedule", modifier = Modifier.size(22.dp), colorFilter = ColorFilter.tint(textIconViewColor()))
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = {},
            modifier = Modifier.padding(end = 16.dp).height(36.dp).alpha(if (isPostGrayedOut.value) 0.3f else 1f),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if (isPostGrayedOut.value) textIconViewColor() else LightBlue)
        ) {
            Text(text = "Post", style = TextStyle(fontSize = 14.sp), textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun AddPostTopHeader() {
    Row(Modifier.padding(top = 24.dp, bottom = 16.dp)) {
        RoundImage(modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "Shubham Bhama",
                modifier = Modifier.padding(0.dp),
                style = TextStyle(fontSize = TextUnit(14F, TextUnitType.Sp), platformStyle = PlatformTextStyle(includeFontPadding = false))
            )
            ActionButton(
                "Anyone", color = textIconViewColor(), modifier = Modifier.padding(top = 6.dp).border(
                    width = 1.dp, color = textIconViewColor(), shape = RoundedCornerShape(24.dp)
                ).padding(horizontal = 8.dp), rightImageResId = Icons.Default.KeyboardArrowDown
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun WritePost(hint: String = "", modifier: Modifier = Modifier, isPostGrayedOut: MutableState<Boolean>) {
    val text = remember { mutableStateOf("") }
    val isHintDisplayed = remember { mutableStateOf(true) }
    Box(modifier) {
        BasicTextField(value = text.value, onValueChange = {
            text.value = it
            isPostGrayedOut.value = text.value.isEmpty()
        }, textStyle = TextStyle(color = textIconViewColor(), fontSize = 16.sp), modifier = Modifier.onFocusChanged {
            isHintDisplayed.value = !it.isFocused && text.value.isEmpty()
        }, cursorBrush = SolidColor(textIconViewColor()))

        if (isHintDisplayed.value) {
            Text(text = hint, color = DarkGray60)
        }
    }
}

@Composable
private fun Footer(modifier: Modifier = Modifier, bottomSheetVisible: MutableState<Boolean>) {
    val context = LocalContext.current
    val takePicture = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {}
    val takeVideo = rememberLauncherForActivityResult(ActivityResultContracts.CaptureVideo()) {}
    val openGallery = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {}
    val requestPermission = AskForPermission()
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.weight(7f), verticalAlignment = Alignment.CenterVertically) {
            FooterIcon(R.drawable.ic_camera, modifier = Modifier.size(24.dp)) {
                context.OpenCamera(takePicture, requestPermission)
            }
            FooterIcon(R.drawable.ic_cam_recorder, modifier = Modifier.size(26.dp)) {
                context.CaptureVideo(takeVideo, requestPermission)
            }
            FooterIcon(R.drawable.ic_gallery, modifier = Modifier.size(30.dp)) {
                openGallery.launch("image/*")
            }
            FooterIcon(R.drawable.ic_menu_vertical, modifier = Modifier.rotate(90f).size(26.dp)) {
                bottomSheetVisible.value = true
            }
        }
        AnyoneWithIcon(modifier = Modifier.weight(3f))
    }
}

@Composable
private fun FooterIcon(resId: Int, modifier: Modifier = Modifier, onClickAction: () -> Unit) {
    Image(
        painter = painterResource(resId),
        contentDescription = "Menu Item",
        modifier = Modifier.padding(horizontal = 8.dp).then(modifier).clickable { onClickAction.invoke() },
        colorFilter = ColorFilter.tint(textIconViewColor())
    )
}

@Composable
private fun AnyoneWithIcon(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = modifier
    ) {
        Icon(painter = painterResource(R.drawable.ic_message_stroke), contentDescription = null, tint = textIconViewColor(), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = "Anyone", fontSize = 14.sp, color = textIconViewColor())
    }
}

private fun Context.OpenCamera(takePicture: ManagedActivityResultLauncher<Void?, Bitmap?>, requestPermission: ManagedActivityResultLauncher<String, Boolean>) {
    if (this.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
        takePicture.launch(null)
    } else {
        requestPermission.launch(Manifest.permission.CAMERA)
    }
}

private fun Context.CaptureVideo(takePicture: ManagedActivityResultLauncher<Uri, Boolean>, requestPermission: ManagedActivityResultLauncher<String, Boolean>) {
    if (this.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
        this.createVideoUri()?.let { takePicture.launch(it) }
    } else {
        requestPermission.launch(Manifest.permission.CAMERA)
    }
}

private fun Context.createVideoUri(): Uri? {
    val fileName = "${System.currentTimeMillis()}.mp4"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
    }
    return contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)
}