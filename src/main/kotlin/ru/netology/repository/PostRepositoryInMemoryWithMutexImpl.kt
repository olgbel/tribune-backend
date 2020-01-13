package ru.netology.repository

import ru.netology.model.PostModel

class PostRepositoryInMemoryWithMutexImpl : PostRepository {
    override suspend fun getRecentPosts(): List<PostModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getPostsAfter(id: Long): List<PostModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getPostsBefore(id: Long): List<PostModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(item: PostModel): PostModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun likeById(id: Long, userId: Long): PostModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun dislikeById(id: Long, userId: Long): PostModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}