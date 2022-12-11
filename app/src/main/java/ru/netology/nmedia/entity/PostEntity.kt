package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val author: String,
    val content: String,
    val date: String,
    var likes: Int,
    var shars: Int,
    val likeByMe: Boolean = false,
    val sharByMe: Boolean = false,
    var video: String? = null

) {
    fun toDto() = Post(
        id = id,
        author = author,
        content = content,
        published = date,
        like = likes,
        shar = shars,
        likeByMe = likeByMe,
        sharByMe = sharByMe,
        video = video
    )


    companion object {
        fun fromDto(post: Post) = PostEntity(
            id = post.id,
            author = post.author,
            content = post.content,
            date = post.published,
            likes = post.like,
            shars = post.shar,
            likeByMe = post.likeByMe,
            sharByMe = post.sharByMe,
            video = post.video
        )
    }
}

