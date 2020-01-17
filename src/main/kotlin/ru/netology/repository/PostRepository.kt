package ru.netology.repository

import ru.netology.dto.UserResponseDto
import ru.netology.model.PostModel
import ru.netology.model.Reaction
import ru.netology.model.UserModel


interface PostRepository {
    suspend fun save(item: PostModel): PostModel
    suspend fun getPostById(postId: Long): PostModel
    suspend fun getRecentPosts(): List<PostModel>
    suspend fun getPostsAfter(id: Long): List<PostModel>
    suspend fun getPostsBefore(id: Long): List<PostModel>
    suspend fun likeById(id: Long, user: UserResponseDto): PostModel?
    suspend fun dislikeById(id: Long, user: UserResponseDto): PostModel?
    suspend fun getReactionsById(postId: Long): List<Reaction>
    suspend fun getPostsByUserId(userId: Long): List<PostModel>
    suspend fun updatePostAuthor(author: UserModel): List<PostModel>
}