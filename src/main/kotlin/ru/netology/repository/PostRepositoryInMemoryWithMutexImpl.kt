package ru.netology.repository

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.netology.model.PostModel
import ru.netology.model.UserModel

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


    override suspend fun likeById(id: Long, user: UserModel): PostModel? {
        mutex.withLock {
            return when (val index = items.indexOfFirst { it.id == id }) {
                -1 -> null
                else -> {
                    val item = items[index]
                    val copy = item.copy(likes = item.likes.plus(user.id))
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

    override suspend fun dislikeById(id: Long, user: UserModel): PostModel? {
        mutex.withLock {
            return when (val index = items.indexOfFirst { it.id == id }) {
                -1 -> null
                else -> {
                    val item = items[index]
                    val copy = item.copy(dislikes = item.dislikes.plus(user.id))
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

}