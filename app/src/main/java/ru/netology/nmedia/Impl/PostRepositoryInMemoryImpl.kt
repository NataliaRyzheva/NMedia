package ru.netology.nmedia.Impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.Repository.PostRepository

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private var post = listOf(
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            like = 0,
            shar = 0,
            likeByMe = false,
            sharByMe = false,
            video = "https://www.youtube.com/watch?v=lokyTAkSPHo"
        ),
        Post(
            id = nextId++,
            author = "ПОСТ 2 Нетология. Университет интернет-профессий будущего",
            content = "ПОСТ 2 Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:37",
            like = 0,
            shar = 0,
            likeByMe = false,
            sharByMe = false
        ),
        Post(
            id = nextId++,
            author = "ПОСТ 3 Нетология. Университет интернет-профессий будущего",
            content = "ПОСТ 3 Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:37",
            like = 0,
            shar = 0,
            likeByMe = false,
            sharByMe = false
        ),
        Post(
            id = nextId++,
            author = "ПОСТ 4 Нетология. Университет интернет-профессий будущего",
            content = "ПОСТ 4 Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:37",
            like = 0,
            shar = 0,
            likeByMe = false,
            sharByMe = false
        )
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<List<Post>> = data
    override fun likeById(id: Long) {
        post = post.map {
            if (it.id != id) {
                it
            } else {
                if (it.likeByMe) {
                    it.copy(likeByMe = !it.likeByMe, like = it.like - 1)
                } else {
                    it.copy(likeByMe = !it.likeByMe, like = it.like + 1)
                }
            }
        }
        data.value = post
    }

    override fun save(postSave: Post) {
        post = if (postSave.id == 0L) {
            listOf(
                postSave.copy(
                    id = nextId++,
                    author = "Netology",
                    published = "Now"
                )
            ) + post
        } else {
            post.map {
                if (it.id != postSave.id) it else it.copy(content = postSave.content)
            }
        }

        data.value = post
    }

    override fun shareById(id: Long) {
        post = post.map {
            if (it.id != id) it
            else
                it.copy(shar = it.shar + 1)
        }
        data.value = post
    }

    override fun removeById(id: Long) {
        post = post.filter { it.id != id }
        data.value = post
    }
}


