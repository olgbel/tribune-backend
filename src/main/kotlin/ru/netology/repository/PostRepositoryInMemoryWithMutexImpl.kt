package ru.netology.repository

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.netology.model.PostModel

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
            return items.subList(Integer.max(items.lastIndex - 19, 0), items.lastIndex + 1).reversed()
        }
    }

////    override suspend fun getPostsAfter(id: Long): List<PostModel> {
////        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
////    }
////
////    override suspend fun getPostsBefore(id: Long): List<PostModel> {
////        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
////    }
////
////    override suspend fun save(item: PostModel): PostModel {
////        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
////    }
////
////    override suspend fun likeById(id: Long, userId: Long): PostModel? {
////        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
////    }
////
////    override suspend fun dislikeById(id: Long, userId: Long): PostModel? {
////        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
////    }

}