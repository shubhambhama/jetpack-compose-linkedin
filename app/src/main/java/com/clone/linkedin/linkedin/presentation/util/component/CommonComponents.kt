package com.clone.linkedin.linkedin.presentation.util.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.clone.linkedin.R
import com.clone.linkedin.ui.theme.textIconViewColor

@Composable
fun RoundImage(
    modifier: Modifier = Modifier, imageResId: Int = R.drawable.user_profile, whoseImage: String? = null
) {
    Image(painter = painterResource(id = imageResId), contentDescription = whoseImage, modifier = modifier.clip(CircleShape))
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