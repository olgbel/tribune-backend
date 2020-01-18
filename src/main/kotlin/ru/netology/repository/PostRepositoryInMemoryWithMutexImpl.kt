package ru.netology.repository

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.netology.dto.UserResponseDto
import ru.netology.model.PostModel
import ru.netology.model.Reaction
import ru.netology.model.ReactionType
import ru.netology.model.UserModel
import java.util.*

class PostRepositoryInMemoryWithMutexImpl : PostRepository {

    private var nextId = 1L
    private val mutex = Mutex()
    private val items = mutableListOf<PostModel>()

    override suspend fun save(item: PostModel): PostModel {
        mutex.withLock {
            val copy = item.copy(id = nextId++)
            items.add(copy)
            return copy
        }
    }

    override suspend fun getPostById(postId: Long): PostModel {
        mutex.withLock {
            val index = items.indexOfFirst { it.id == postId }
            return items[index]
        }
    }


    override suspend fun getRecentPosts(): List<PostModel> {
        mutex.withLock {
            return items.subList(Integer.max(items.lastIndex - 19, 0), items.lastIndex + 1)
                .reversed()
        }
    }

    override suspend fun getPostsAfter(id: Long): List<PostModel> {
        mutex.withLock {
            val filteredList = items.filter { it.id > id } as MutableList<PostModel>
            return filteredList.reversed()
        }
    }

    override suspend fun getPostsBefore(id: Long): List<PostModel> {
        mutex.withLock {
            val filteredList = items.filter { it.id < id } as MutableList<PostModel>
            return filteredList.reversed()
        }
    }


    override suspend fun likeById(id: Long, user: UserResponseDto): PostModel? {
        mutex.withLock {
            return when (val index = items.indexOfFirst { it.id == id }) {
                -1 -> null
                else -> {
                    val item = items[index]
                    val copy = item.copy(
                        likes = item.likes.plus(
                            Reaction(
                                user,
                                Date().time,
                                ReactionType.LIKE
                            )
                        )
                    )
                    try {
                        items[index] = copy
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        println("size: ${items.size}")
                        println(index)
                    }
                    copy
                }
            }
        }
    }

    override suspend fun dislikeById(id: Long, user: UserResponseDto): PostModel? {
        mutex.withLock {
            return when (val index = items.indexOfFirst { it.id == id }) {
                -1 -> null
                else -> {
                    val item = items[index]
                    val copy = item.copy(
                        dislikes = item.dislikes.plus(
                            Reaction(
                                user,
                                Date().time,
                                ReactionType.DISLIKE
                            )
                        )
                    )
                    try {
                        items[index] = copy
                    } catch (e: ArrayIndexOutOfBoundsException) {
                        println("size: ${items.size}")
                        println(index)
                    }
                    copy
                }
            }
        }
    }

    override suspend fun getReactionsById(postId: Long): List<Reaction> {
        mutex.withLock {
            return when (val index = items.indexOfFirst { it.id == postId }) {
                -1 -> emptyList()
                else -> {
                    val item = items[index]
                    item.likes.plus(item.dislikes).toList()
                }
            }
        }
    }

    override suspend fun getPostsByUserId(userId: Long): List<PostModel> {
        mutex.withLock {
            return items.filter { it.author.id == userId }
        }
    }

    override suspend fun updatePostAuthor(author: UserModel): List<PostModel> {
        mutex.withLock {
            val posts = items.filter { it.author.id == author.id }
            posts.forEach {
                val copy = it.copy(author = author)
                val index = items.indexOf(it)
                items[index] = copy
            }
            return items
        }
    }

}