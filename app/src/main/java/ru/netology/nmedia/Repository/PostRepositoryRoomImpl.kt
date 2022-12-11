package ru.netology.nmedia.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import ru.netology.nmedia.Post
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.entity.PostEntity

class PostRepositoryRoomImpl(
    private val dao: PostDao
) : PostRepository {


    override fun get() = dao.get().map { list -> list.map { it.toDto() } }

    override fun save(postSave: Post) = dao.save(PostEntity.fromDto(postSave))

    override fun likeById(id: Long) = dao.likeById(id)

    override fun removeById(id: Long) = dao.removeById(id)

    override fun shareById(id: Long) = dao.shareById(id)

}
