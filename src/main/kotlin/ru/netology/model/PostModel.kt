package ru.netology.model

data class PostModel(
    val id: Long = -1,
    val author: UserModel,
    val content: String,
    val created: Long,
    val likes: Set<Long> = setOf(),
    val dislikes: Set<Long> = setOf(),
    val linkURL: String? = null,
    val attachment: AttachmentModel
)
