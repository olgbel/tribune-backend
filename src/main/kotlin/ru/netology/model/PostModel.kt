package ru.netology.model

data class PostModel(
    val id: Long = -1,
    val author: UserModel,
    val content: String,
    val created: Int = (System.currentTimeMillis() / 1000).toInt(),
    val likes: Set<Long> = setOf(),
    val dilsikes: Set<Long> = setOf(),
    val linkURL: String? = null,
    val attachment: AttachmentModel
)
