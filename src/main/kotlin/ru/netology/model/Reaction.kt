package ru.netology.model

import ru.netology.dto.UserResponseDto

enum class ReactionType {
    LIKE, DISLIKE
}

data class Reaction (
    val user: UserResponseDto,
    val date: Long,
    val type: ReactionType
)