package ru.netology.repository

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.netology.model.UserModel

class UserRepositoryInMemoryWithMutexImpl : UserRepository {
    private var nextId = 1L
    private val items = mutableListOf<UserModel>()
    private val mutex = Mutex()

    override suspend fun getAll(): List<UserModel> {
        mutex.withLock {
            return items.toList()
        }
    }

    override suspend fun getById(id: Long): UserModel? {
        mutex.withLock {
            return items.find { it.id == id }
        }
    }

    override suspend fun getByIds(ids: Collection<Long>): List<UserModel> {
        mutex.withLock {
            return items.filter { ids.contains(it.id) }
        }
    }

    override suspend fun getByUsername(username: String): UserModel? {
        mutex.withLock {
            return items.find { it.username == username }
        }
    }

    override suspend fun save(item: UserModel): UserModel {
        mutex.withLock {
            return when (val index = items.indexOfFirst { it.id == item.id }) {
                -1 -> {
                    val copy = item.copy(id = nextId++)
                    items.add(copy)
                    copy
                }
                else -> {
                    val copy = items[index].copy(
                        username = item.username,
                        password = item.password,
                        token = item.token,
                        avatar = item.avatar
                    )
                    items[index] = copy
                    copy
                }
            }
        }
    }

    override suspend fun update(item: UserModel): UserModel {
        mutex.withLock {
            val user = items.find { it.id == item.id }
            val index = items.indexOfFirst { it.id == user?.id }
            val copy = items[index].copy(avatar = item.avatar)
            items[index] = copy
            return copy
        }
    }
}