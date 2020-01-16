package ru.netology.dto

import ru.netology.model.AttachmentModel
import ru.netology.model.PostModel
import ru.netology.model.Reaction
import ru.netology.model.UserModel

data class PostResponseDto(
    val id: Long,
    val author: UserResponseDto,
    val content: String,
    val created: Long,
    val likes: Set<Reaction> = setOf(),
    val isLikedByMe: Boolean,
    val dislikes: Set<Reaction> = setOf(),
    val isDislikedByMe: Boolean,
    val linkURL: String?,
    val attachment: AttachmentModel
) {
    companion object {
        fun fromModel(currentUser: UserModel, postAuthor: UserModel, model: PostModel) = PostResponseDto(
            id = model.id,
            author = UserResponseDto.fromModel(postAuthor),
            content = model.content,
            created = model.created,
            likes = model.likes,
            isLikedByMe = model.likes.any { it.user.id == currentUser.id },
            dislikes = model.dislikes,
            isDislikedByMe = model.dislikes.any { it.user.id == currentUser.id },
            linkURL = model.linkURL,
            attachment = model.attachment
        )
    }
}