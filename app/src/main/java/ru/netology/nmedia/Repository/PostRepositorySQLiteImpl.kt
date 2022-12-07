package ru.netology.nmedia.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.dao.PostDao

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {
    private var post = emptyList<Post>()
    private val data = MutableLiveData(post)

    init {
        post = dao.get()
        data.value = post
    }

    override fun get(): LiveData<List<Post>> = data

    override fun save(postSave: Post) {
        val id = postSave.id
        val saved = dao.save(postSave)
        post = if (id == 0L) {
            listOf(saved) + post
        } else {
            post.map {
                if (it.id != id) it else saved
            }
        }
        data.value = post
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
        post = post.map {
            if (it.id != id) it else it.copy(
                likeByMe = !it.likeByMe,
                like = if (it.likeByMe) it.like - 1 else it.like + 1
            )
        }
        data.value = post
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
        post = post.filter { it.id != id }
        data.value = post
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
        post = post.map {
            if (it.id != id) it
            else it.copy(
                shar = it.shar + 1
            )
        }
        data.value = post
    }
}