package com.clone.linkedin.linkedin.presentation.util.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.OriginalSize
import com.clone.linkedin.R
import com.clone.linkedin.ui.theme.LightBlue
import com.clone.linkedin.ui.theme.textIconViewColor
import kotlin.random.Random

@Composable
fun RoundImage(
    modifier: Modifier = Modifier, imageResId: Int = R.drawable.user_profile, whoseImage: String? = null
) {
    Image(painter = painterResource(id = imageResId), contentDescription = whoseImage, modifier = modifier.clip(CircleShape))
}

@Composable
fun Context.ImageViaUrl(modifier: Modifier = Modifier, imageUrl: String, shape: Shape) {
    val imageRequest = ImageRequest.Builder(this)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .size(OriginalSize)
        .crossfade(true)
        .data(imageUrl).build()
    val asyncImage = rememberAsyncImagePainter(imageRequest)
    Image(
        painter = asyncImage, contentDescription = null, modifier = modifier
            .clip(shape), contentScale = ContentScale.Crop)
}


@Composable
fun ExpandableText(
    modifier: Modifier = Modifier, text: String, maxLines: Int, seeMoreText: String = "see more", seeLessText: String = "see less", defaultExpand:
    Boolean = false
) {
    val isExpanded = remember { mutableStateOf(false) }
    val spannableText = buildAnnotatedString {
        if (defaultExpand || isExpanded.value) {
            append(text = text)
            append(" ")
            pushStringAnnotation("clickable", annotation = seeLessText)
            withStyle(SpanStyle(textDecoration = TextDecoration.None, color = textIconViewColor())) {
                append(seeLessText)
            }
        } else {
            val truncatedString = buildString {
                append(text = text.take(maxLines * 30))
            }
            append(truncatedString)
            append("...")
            pushStringAnnotation("clickable", annotation = seeMoreText)
            withStyle(SpanStyle(textDecoration = TextDecoration.None, color = textIconViewColor())) {
                append(seeMoreText)
            }
            pop()
        }
    }
    val textStyle = TextStyle(fontSize = 13.sp, color = textIconViewColor())
    ClickableText(text = spannableText, style = textStyle, onClick = { offset ->
        spannableText.getStringAnnotations(tag = "clickable", start = offset, end = offset).firstOrNull()?.let {
            isExpanded.value = !isExpanded.value
        }
    }, modifier = modifier)
}

@Composable
fun currentRoute(modifier: Modifier = Modifier, navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun ActionButton(text: String, color: Color = LightBlue, rightImageResId: ImageVector? = null, modifier: Modifier) {
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
        modifier = modifier) {
        Text(text = text, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = color)
        rightImageResId?.let { Icon(imageVector = rightImageResId, contentDescription = null, tint = color) }
    }
}