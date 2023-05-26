package com.clone.linkedin.linkedin.presentation.dashboard.component

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.clone.linkedin.R
import com.clone.linkedin.linkedin.data.constants.enums.PostButtonAction
import com.clone.linkedin.linkedin.presentation.dashboard.DashboardViewModel
import com.clone.linkedin.linkedin.presentation.dashboard.NormalPost
import com.clone.linkedin.linkedin.presentation.dashboard.PostAction
import com.clone.linkedin.linkedin.presentation.dashboard.PostHeader
import com.clone.linkedin.linkedin.presentation.util.component.ExpandableText
import com.clone.linkedin.linkedin.presentation.util.component.ImageViaUrl
import com.clone.linkedin.linkedin.presentation.util.component.LinkedInBottomSheet
import com.clone.linkedin.ui.theme.LightBlue
import com.clone.linkedin.ui.theme.textIconViewColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(navController: NavController) {
    val bottomSheetVisible = remember { mutableStateOf(false) }
    val viewModel: DashboardViewModel = hiltViewModel()
    Box(Modifier.fillMaxSize().background(Color.Black)) {
        PostList(bottomSheetVisible = bottomSheetVisible, viewModel = viewModel)
        if (bottomSheetVisible.value) {
            LinkedInBottomSheet(viewModel.getDataForPostMenu()) {
                bottomSheetVisible.value = false
            }
        }
    }
}

@Composable
private fun PostList(viewModel: DashboardViewModel, bottomSheetVisible: MutableState<Boolean>) {
    val posts = viewModel.postState.value
    val context = LocalContext.current

    LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
        items(posts.size) {
            val data = posts[it]
            if (data is NormalPost) {
                NormalPostItem(post = data, modifier = Modifier, bottomSheetVisible = bottomSheetVisible, context = context)
            }
        }
    }
}

@Composable
private fun NormalPostItem(modifier: Modifier = Modifier, post: NormalPost, bottomSheetVisible: MutableState<Boolean>, context: Context) {
    val likeButtonState = remember { mutableStateOf(false) }
    val commentButtonState = remember { mutableStateOf(false) }
    val repostButtonState = remember { mutableStateOf(false) }
    val sendButtonState = remember { mutableStateOf(false) }
    var isLikeButtonVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Surface(modifier.padding(bottom = 8.dp)) {
        Column(modifier = Modifier.padding(top = 8.dp)) {
            post.postHeader?.let {
                PostHeader(
                    postHeader = post.postHeader,
                    modifier = Modifier.padding(start = 8.dp), bottomSheetVisible = bottomSheetVisible, context = context
                )
            }
            Row(modifier = Modifier.padding(start = 16.dp)) {
                context.ImageViaUrl(modifier = Modifier.size(48.dp), post.postTop.userProfileImageUrl, CircleShape)
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = post.postTop.userName,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = TextUnit(14F, TextUnitType.Sp))
                    )
                    Text(text = post.postTop.information, fontSize = TextUnit(12F, TextUnitType.Sp))
                    Text(text = post.postTop.postTime, fontSize = TextUnit(12F, TextUnitType.Sp))
                }
                ConnectFollowJoin(Pair("Follow", R.drawable.ic_add), Pair("Following", R.drawable.ic_tick))
            }
            ExpandableText(
                modifier = Modifier.animateContentSize().padding(start = 16.dp, top = 4.dp, bottom = 8.dp, end = 4.dp),
                text = post.postCenter.caption,
                maxLines = 2
            )
            Box {
                context.ImageViaUrl(modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                    detectTapGestures(onDoubleTap = {
                        likeButtonState.value = true
                        isLikeButtonVisible = true

                        coroutineScope.launch {
                            delay(1000)
                            isLikeButtonVisible = false
                        }
                    })
                }, post.postCenter.postImageUrl, RectangleShape)
                if (isLikeButtonVisible) {
                    DoubleTapImage(modifier = Modifier.size(70.dp).align(Alignment.Center))
                }
            }
            LikeShareCommentInfo(
                modifier = Modifier.padding(10.dp),
                postAction = post.postAction,
                likeButtonState,
                commentButtonState,
                repostButtonState
            )
            Divider(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).height(1.dp))
            PostAction(likeButtonState, commentButtonState, repostButtonState, sendButtonState)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun PostHeader(modifier: Modifier = Modifier, postHeader: PostHeader, bottomSheetVisible: MutableState<Boolean>, context: Context) {
    val interactionSource = remember { MutableInteractionSource() }
    Column(Modifier.fillMaxSize()) {
        Row(modifier) {
            context.ImageViaUrl(modifier = Modifier.size(28.dp), postHeader.actionUserImageUrl, CircleShape)
            Spacer(Modifier.width(8.dp))
            Text(text = postHeader.information, textAlign = TextAlign.Start, fontSize = 12.sp, modifier = Modifier.align(Alignment.CenterVertically).weight(8f))
            Image(
                painter = painterResource(R.drawable.ic_menu_vertical),
                contentDescription = "Menu Item",
                modifier = Modifier.size(20.dp).weight(1f).align(Alignment.CenterVertically)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { bottomSheetVisible.value = true },
                colorFilter = ColorFilter.tint(textIconViewColor())
            )
        }
        Divider(modifier = Modifier.padding(8.dp).height(1.dp))
    }
}

@Composable
fun LikeShareCommentInfo(
    modifier: Modifier = Modifier, postAction: PostAction, likeButtonState: MutableState<Boolean>,
    commentButtonState: MutableState<Boolean>,
    repostButtonState: MutableState<Boolean>
) {
    val likeText = if (likeButtonState.value) "You and ${postAction.likes} others" else postAction.likes.toString()
    val commentText = if (commentButtonState.value) "${postAction.comments + 1} comments" else "${postAction.comments} comments"
    val repostText = if (repostButtonState.value) "${postAction.share + 1} reposts" else "${postAction.share} reposts"

    Row(modifier) {
        Row(modifier = Modifier.weight(1f)) {
            Image(
                painter = painterResource(R.drawable.ic_circle_like),
                modifier = Modifier.padding(start = 8.dp).size(18.dp).clip(CircleShape).border(width = 0.6.dp, shape = CircleShape, color = textIconViewColor()),
                contentDescription = null,
            )
            Text(text = likeText, fontSize = TextUnit(12F, TextUnitType.Sp), textAlign = TextAlign.Center, modifier = Modifier.padding(start = 5.dp))
        }
        Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.weight(1f)) {
            Row {
                Text(text = "$commentText • $repostText", fontSize = TextUnit(12F, TextUnitType.Sp), textAlign = TextAlign.Left)
            }
        }
    }
}

@Composable
fun PostAction(
    likeButtonState: MutableState<Boolean>,
    commentButtonState: MutableState<Boolean>,
    repostButtonState: MutableState<Boolean>,
    sendButtonState: MutableState<Boolean>
) {
    Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        PostActionElement(if (likeButtonState.value) R.drawable.ic_reaction_like else R.drawable.ic_thumbs_up, PostButtonAction.LIKE,
            if (likeButtonState.value) null else textIconViewColor()
        ) {
            likeButtonState.value = !likeButtonState.value
        }
        PostActionElement(R.drawable.ic_comment, PostButtonAction.COMMENT) {
            commentButtonState.value = !commentButtonState.value
        }
        PostActionElement(R.drawable.ic_repost, PostButtonAction.REPOST) {
            repostButtonState.value = !repostButtonState.value
        }
        PostActionElement(R.drawable.ic_send, PostButtonAction.SEND) {
            sendButtonState.value = !sendButtonState.value
        }
    }
}

@Composable
private fun PostActionElement(icon: Int, postButton: PostButtonAction, color: Color? = textIconViewColor(), onClick: (PostButtonAction) -> Unit) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick.invoke(postButton) }) {
        Image(
            painter = painterResource(icon),
            modifier = Modifier.size(23.dp).padding(2.dp),
            contentDescription = postButton.value,
            colorFilter = color?.let { ColorFilter.tint(color) }
        )
        Text(text = postButton.value, fontSize = TextUnit(12F, TextUnitType.Sp), color = textIconViewColor())
    }
}

@Composable
private fun ConnectFollowJoin(beforeActionButton: Pair<String, Int?>, afterActionButton: Pair<String, Int?>) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFollowButton = remember { mutableStateOf(true) }
    val whichButton = if (isFollowButton.value) beforeActionButton else afterActionButton
    val viewColor = if (isFollowButton.value) LightBlue else textIconViewColor()
    Row(
        modifier = Modifier.fillMaxWidth().padding(end = 16.dp).clickable(indication = null, interactionSource = interactionSource) {
            isFollowButton.value = !isFollowButton.value
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        whichButton.second?.let { icon ->
            Image(
                painter = painterResource(icon),
                modifier = Modifier.size(20.dp).padding(2.dp),
                contentDescription = whichButton.first,
                colorFilter = ColorFilter.tint(viewColor)
            )
        }
        Text(text = whichButton.first, fontSize = TextUnit(14F, TextUnitType.Sp), color = viewColor)
    }
}

@Composable
private fun DoubleTapImage(modifier: Modifier = Modifier) {
    var isOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isOpen = true
        delay(2000)
        isOpen = false
    }

    val scale by animateFloatAsState(
        targetValue = if (isOpen) 1f else 0.9f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Image(painter = painterResource(id = R.drawable.ic_like_fill), contentDescription = null, modifier = modifier
        .scale(scale), colorFilter = ColorFilter.tint(textIconViewColor()))
}