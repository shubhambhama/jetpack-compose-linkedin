package com.clone.linkedin.linkedin.presentation.dashboard

import androidx.annotation.Keep

@Keep
data class NormalPost(
    val postHeader: PostHeader? = null,
    val postTop: PostTop,
    val postCenter: PostCenter,
    val postAction: PostAction
) : PostType("post_normal")

@Keep
data class VotePost(
    val postTop: PostTop,
    val caption: String,
    val options: List<String>
) : PostType("post_vote")

@Keep
data class PromotedPost(
    val postTop: PostTop,
    val postCenter: PostCenter,
    val metadata: PostDetailAndUrl,
    val postAction: PostAction
) : PostType("post_promoted")

@Keep
data class RecommendationPost(
    val postTop: String,
    val listOfEntity: List<PostTop>
) : PostType("post_recommendation")

@Keep
data class PostHeader(val actionUserImage: Int, val information: String)

@Keep
data class PostTop(val userProfileImage: Int, val userName: String,
                   val information: String, val postTime: String)

@Keep
data class PostCenter(val caption: String, val postImage: Int)

@Keep
data class PostDetailAndUrl(val information: String, val url: String)

@Keep
data class PostAction(val likes: Int, val comments: Int, val share: Int)