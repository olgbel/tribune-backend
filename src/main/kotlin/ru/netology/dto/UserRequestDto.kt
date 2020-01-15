package ru.netology.dto

import ru.netology.model.AttachmentModel

data class UserRequestDto (
    val userId: Long,
    val attachment: AttachmentModel
)