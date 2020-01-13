package ru.netology.repository

import ru.netology.model.PostModel


interface PostRepository {
    suspend fun getRecentPosts(): List<PostModel>
    suspend fun getPostsAfter(id: Long): List<PostModel>
    suspend fun getPostsBefore(id: Long): List<PostModel>
    suspend fun save(item: PostModel): PostModel
    suspend fun likeById(id: Long, userId: Long): PostModel?
    suspend fun dislikeById(id: Long, userId: Long): PostModel?
}