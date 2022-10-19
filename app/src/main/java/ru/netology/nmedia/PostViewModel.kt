package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data: LiveData<List<Post>> = repository.get()
    fun like(id: Long) = repository.like(id)
    fun shar(id: Long) = repository.shar(id)
}