package ru.netology.service

import io.ktor.features.NotFoundException
import ru.netology.dto.PostResponseDto
import ru.netology.repository.PostRepository
import ru.netology.repository.UserRepository

class PostService(private val repo: PostRepository,
                  private val repoUser: UserRepository,
                  private val userService: UserService){

    suspend fun getRecentPosts(): List<PostResponseDto> {
        return repo.getRecentPosts().map { PostResponseDto.fromModel(it)}
    }

    suspend fun getPostsAfter(id: Long): List<PostResponseDto>  {
        return repo.getPostsAfter(id).map { PostResponseDto.fromModel(it) }
    }

    suspend fun getPostsBefore(id: Long): List<PostResponseDto> {
        return repo.getPostsBefore(id).map { PostResponseDto.fromModel(it) }
    }

    suspend fun likeById(postId: Long, userId: Long): PostResponseDto {
        val model = repo.likeById(postId, userId) ?: throw NotFoundException()
        val likeText = if (model.content?.length ?: 0 > 15) model.content?.substring(0, 15) + "..." else model.content
        return PostResponseDto.fromModel(model)
    }

    suspend fun dislikeById(id: Long, userId: Long): PostResponseDto {
        val model = repo.dislikeById(id, userId) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }
}