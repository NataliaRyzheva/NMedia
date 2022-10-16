package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "21 мая в 18:36",
        like = 0,
        shar = 0,
        likeByMe = false,
        sharByMe = false
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        post = if (post.likeByMe) {
            post.copy(likeByMe = !post.likeByMe, like = post.like - 1)
        } else {
            post.copy(likeByMe = !post.likeByMe, like = post.like + 1)
        }
        data.value = post
    }

    override fun shar() {
        post = post.copy(sharByMe = !post.sharByMe, shar = post.shar + 1)
        data.value = post
    }

}