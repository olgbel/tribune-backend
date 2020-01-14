package ru.netology.service

import io.ktor.features.NotFoundException
import ru.netology.dto.PostRequestDto
import ru.netology.dto.PostResponseDto
import ru.netology.model.PostModel
import ru.netology.model.UserModel
import ru.netology.repository.PostRepository
import ru.netology.repository.UserRepository
import java.util.*

class PostService(private val repo: PostRepository,
                  private val userService: UserService){

    suspend fun save(input: PostRequestDto, user: UserModel): PostResponseDto {
        val model =
                PostModel(
                    id = 0L,
                    author = user,
                    created = Date().time,
                    content = input.content,
                    linkURL = input.linkURL,
                    attachment = input.attachment
                )
        return PostResponseDto.fromModel(user, repo.save(model))
    }

    suspend fun getRecentPosts(user: UserModel): List<PostResponseDto> {
        return repo.getRecentPosts().map { PostResponseDto.fromModel(user, it)}
    }

    suspend fun getPostsAfter(user: UserModel, id: Long): List<PostResponseDto>  {
        return repo.getPostsAfter(id).map { PostResponseDto.fromModel(user, it) }
    }

    suspend fun getPostsBefore(user: UserModel, id: Long): List<PostResponseDto> {
        return repo.getPostsBefore(id).map { PostResponseDto.fromModel(user, it) }
    }

    suspend fun likeById(postId: Long, user: UserModel): PostResponseDto {
        val model = repo.likeById(postId, user) ?: throw NotFoundException()
        return PostResponseDto.fromModel(user, model)
    }

    suspend fun dislikeById(id: Long, user: UserModel): PostResponseDto {
        val model = repo.dislikeById(id, user) ?: throw NotFoundException()
        return PostResponseDto.fromModel(user, model)
    }
}