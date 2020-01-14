package ru.netology.dto

import ru.netology.model.AttachmentModel

data class PostRequestDto(
    val content: String,
    val linkURL: String?,
    val attachment: AttachmentModel
)