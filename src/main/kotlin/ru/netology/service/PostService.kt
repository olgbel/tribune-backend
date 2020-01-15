package ru.netology.service

import io.ktor.features.NotFoundException
import io.ktor.util.KtorExperimentalAPI
import ru.netology.dto.PostRequestDto
import ru.netology.dto.PostResponseDto
import ru.netology.dto.ReactionResponseDto
import ru.netology.dto.UserResponseDto
import ru.netology.model.PostModel
import ru.netology.model.Reaction
import ru.netology.model.UserModel
import ru.netology.repository.PostRepository
import java.util.*

@KtorExperimentalAPI
class PostService(
    private val repo: PostRepository,
    private val userService: UserService
) {

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
        return PostResponseDto.fromModel(user, user, repo.save(model))
    }

    suspend fun getRecentPosts(currentUser: UserModel): List<PostResponseDto> {
        return repo.getRecentPosts().map { PostResponseDto.fromModel(currentUser, currentUser, it) }
    }

    suspend fun getPostsAfter(currentUser: UserModel, id: Long): List<PostResponseDto> {
        return repo.getPostsAfter(id)
            .map { PostResponseDto.fromModel(currentUser, currentUser, it) }
    }

    suspend fun getPostsBefore(currentUser: UserModel, id: Long): List<PostResponseDto> {
        return repo.getPostsBefore(id)
            .map { PostResponseDto.fromModel(currentUser, currentUser, it) }
    }

    suspend fun likeById(postId: Long, user: UserModel): PostResponseDto {
        val model =
            repo.likeById(postId, UserResponseDto.fromModel(user)) ?: throw NotFoundException()
        return PostResponseDto.fromModel(user, userService.getModelById(model.author.id)!!, model)
    }

    suspend fun dislikeById(id: Long, user: UserModel): PostResponseDto {
        val model =
            repo.dislikeById(id, UserResponseDto.fromModel(user)) ?: throw NotFoundException()
        return PostResponseDto.fromModel(user, userService.getModelById(model.author.id)!!, model)
    }

    suspend fun getReactionsById(id: Long): List<ReactionResponseDto> {
        return repo.getReactionsById(id).map { ReactionResponseDto.fromModel(it) }
    }
}