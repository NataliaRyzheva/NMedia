package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var like: Int,
    var shar: Int,
    val likeByMe: Boolean = false,
    val sharByMe: Boolean = false

)
