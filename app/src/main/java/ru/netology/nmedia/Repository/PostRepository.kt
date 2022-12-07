package ru.netology.nmedia.Repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {
    fun get(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun removeById(id: Long)
    fun shareById(id: Long)

    // fun like(id: Long)
    // fun shar(id: Long)
    fun save(post: Post)

}