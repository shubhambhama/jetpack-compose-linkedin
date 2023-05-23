package com.clone.linkedin.linkedin.presentation.dashboard

sealed class PostType(val type: String) {
    class NormalPost : PostType("post_normal")
    object RecommendationPost : PostType("post_recommendation")
    object PromotedPost : PostType("post_promoted")
    object VotePost : PostType("post_vote")
}