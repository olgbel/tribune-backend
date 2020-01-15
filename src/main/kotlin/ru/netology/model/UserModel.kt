package ru.netology.model

import io.ktor.auth.Principal

data class UserModel (
    val id: Long = 0,
    val username: String,
    val avatar: AttachmentModel? = null,
    val password: String,
    val token: PushToken? = null
): Principal