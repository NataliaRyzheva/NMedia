package ru.netology.nmedia

import androidx.core.view.isVisible
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
    sharByMe = false,
    video = ""

)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data: LiveData<List<Post>> = repository.get()
    val edited = MutableLiveData(empty)


    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        } else {
            edited.value = edited.value?.copy(content = text)
        }
    }

    fun changeContentUrl(video: String) {
        val videoUrl = video.trim()
        if (edited.value?.video == videoUrl) {
            return
        } else {
            edited.value = edited.value?.copy(video = videoUrl)
        }
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




