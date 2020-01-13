package ru.netology.dto

import ru.netology.model.AttachmentModel

data class PostRequestDto(
    val author: Long,
    val content: String,
    val linkURL: String? = null,
    val attachment: AttachmentModel
)