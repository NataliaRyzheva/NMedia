package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var like: Int,
    var shar: Int,
    var likeByMe: Boolean = false,
    var sharByMe: Boolean = false

)
