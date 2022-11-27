package ru.netology.nmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

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

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryFilesImpl(application)
    val data: LiveData<List<Post>> = repository.get()
    val edited = MutableLiveData(empty)
    val draftContent = MutableLiveData("")

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

    fun clearEdited() {
        edited.value = empty
    }

    fun clearDraft() {
        draftContent.value = ""
    }

}




