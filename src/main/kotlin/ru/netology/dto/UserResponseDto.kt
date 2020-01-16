package ru.netology.dto

import ru.netology.model.AttachmentModel
import ru.netology.model.UserModel

data class UserResponseDto(val id: Long, val username: String, val avatar: AttachmentModel?, val isReadOnly: Boolean) {
    companion object {
        fun fromModel(model: UserModel) = UserResponseDto(
            id = model.id,
            username = model.username,
            avatar = model.avatar,
            isReadOnly = model.isReadOnly
        )
    }
}