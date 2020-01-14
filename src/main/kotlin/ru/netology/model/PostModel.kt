package ru.netology.model

data class PostModel(
    val id: Long = -1,
    val author: UserModel,
    val content: String,
    val created: Long,
    val likes: Set<Reaction> = setOf(),
    val dislikes: Set<Reaction> = setOf(),
    val linkURL: String? = null,
    val attachment: AttachmentModel
)
