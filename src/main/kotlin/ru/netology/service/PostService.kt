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

//    suspend fun getPostsAfter(id: Long): List<PostResponseDto>  {
//        return repo.getPostsAfter(id).map { PostResponseDto.fromModel(it) }
//    }
//
//    suspend fun getPostsBefore(id: Long): List<PostResponseDto> {
//        return repo.getPostsBefore(id).map { PostResponseDto.fromModel(it) }
//    }
//
//    suspend fun likeById(postId: Long, user: UserModel): PostResponseDto {
//        val model = repo.likeById(postId, userId) ?: throw NotFoundException()
//        val likeText = if (model.content?.length ?: 0 > 15) model.content?.substring(0, 15) + "..." else model.content
//        return PostResponseDto.fromModel()
//
//        userService.increasePromotes(user.id)
//        recalcBadgeforUser()
//        return PostResponseDto.fromModel(user, userService.getModelById(model.author)!!, model)
//    }
//
//    suspend fun dislikeById(id: Long, userId: Long): PostResponseDto {
//        val model = repo.dislikeById(id, userId) ?: throw NotFoundException()
//        return PostResponseDto.fromModel(model)
//    }
}