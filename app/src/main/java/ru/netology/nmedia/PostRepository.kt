package ru.netology.nmedia

import androidx.lifecycle.LiveData

interface PostRepository {
    fun get(): LiveData<List<Post>>
    fun like(id: Long)
    fun shar(id: Long)
}