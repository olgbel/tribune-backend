package ru.netology.dto

import ru.netology.model.AttachmentModel
import ru.netology.model.PostModel
import ru.netology.model.UserModel

data class PostResponseDto(val id: Long,
                           val author: UserResponseDto,
                           val content: String,
                           val created: Long,
                           val likes: Set<Long> = setOf(),
                           val dislikes: Set<Long> = setOf(),
                           val linkURL: String?,
                           val attachment: AttachmentModel) {
    companion object {
        fun fromModel(user: UserModel, model: PostModel) = PostResponseDto(
            id = model.id,
            author = UserResponseDto.fromModel(user),
            content = model.content,
            created = model.created,
            likes = model.likes,
            dislikes = model.dislikes,
            linkURL = model.linkURL,
            attachment = model.attachment
        )
    }
}