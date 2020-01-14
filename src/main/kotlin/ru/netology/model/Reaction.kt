package ru.netology.model

enum class ReactionType {
    LIKE, DISLIKE
}

data class Reaction (
    val userId: Long,
    val date: Long,
    val type: ReactionType
)