package ru.netology.dto

import ru.netology.model.AttachmentModel
import ru.netology.model.UserModel

data class UserResponseDto(val id: Long, val username: String, val attachmentModel: AttachmentModel?) {
    companion object {
        fun fromModel(model: UserModel) = UserResponseDto(
            id = model.id,
            username = model.username,
            attachmentModel = model.avatar
        )
    }
}