package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    like = 0,
    shar = 0,
    likeByMe = false,
    sharByMe = false

)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data: LiveData<List<Post>> = repository.get()
    val edited = MutableLiveData(empty)

    fun changeContent(content: String) {
        val text = content.trim()
        edited.value?.let {
            if (it.content == text)
                return
        }
        edited.value = edited.value?.copy(content = text)
    }


    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun like(id: Long) = repository.like(id)
    fun shar(id: Long) = repository.shar(id)
    fun removeById(id: Long) = repository.removerById(id)
    fun edit(post: Post) {
        edited.value = post
    }

}