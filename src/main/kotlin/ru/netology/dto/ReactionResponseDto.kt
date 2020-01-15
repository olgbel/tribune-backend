package ru.netology.dto

import ru.netology.model.Reaction
import ru.netology.model.ReactionType

data class ReactionResponseDto (
    val user: UserResponseDto,
    val date: Long,
    val type: ReactionType
){
    companion object {
        fun fromModel(model: Reaction) = ReactionResponseDto(
            user = model.user,
            date = model.date,
            type = model.type
        )
    }
}