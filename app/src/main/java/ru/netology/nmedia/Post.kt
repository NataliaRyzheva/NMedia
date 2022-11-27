package ru.netology.nmedia

import java.io.Serializable

data class Post(
    var id: Long,
    val author: String,
    val content: String,
    val published: String,
    var like: Int,
    var shar: Int,
    val likeByMe: Boolean = false,
    val sharByMe: Boolean = false,
    var video: String? = null
) : Serializable


