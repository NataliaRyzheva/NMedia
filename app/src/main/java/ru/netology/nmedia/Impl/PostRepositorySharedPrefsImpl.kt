package ru.netology.nmedia.Impl

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.Post
import ru.netology.nmedia.Repository.PostRepository

class PostRepositorySharedPrefsImpl(context: Context) : PostRepository {
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val key = "post"
    private val typeToken = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private var nextId = 1L
    private var post = emptyList<Post>()
    private val data = MutableLiveData(post)

    init {
        prefs.getString(key, null)?.let {
            post = gson.fromJson(it, typeToken)
            nextId = (post.maxOfOrNull { it.id } ?: 0) + 1
        } ?: run {
            post = listOf(
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
            ).reversed()
        }
        data.value = post
    }

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
        sync()
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
        sync()

    }

    override fun shareById(id: Long) {
        post = post.map {
            if (it.id != id) it
            else
                it.copy(shar = it.shar + 1)
        }
        data.value = post
        sync()
    }

    override fun removeById(id: Long) {
        post = post.filter { it.id != id }
        data.value = post
        sync()
    }

    private fun sync() {
        prefs.edit().apply {
            putString(key, gson.toJson(post))
            apply()
        }
    }
}


