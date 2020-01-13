package ru.netology.dto

import ru.netology.model.AttachmentModel
import ru.netology.model.PostModel
import ru.netology.model.UserModel

data class PostResponseDto(val id: Long,
                           val author: UserModel,
                           val content: String,
                           val created: Int,
                           val likes: Set<Long> = setOf(),
                           val dislikes: Set<Long> = setOf(),
                           val linkURL: String? = null,
                           val attachment: AttachmentModel) {
    companion object {
        fun fromModel(model: PostModel) = PostResponseDto(
            id = model.id,
            author = model.author,
            content = model.content,
            created = model.created,
            likes = model.likes,
            linkURL = model.linkURL,
            attachment = model.attachment
        )
    }
}