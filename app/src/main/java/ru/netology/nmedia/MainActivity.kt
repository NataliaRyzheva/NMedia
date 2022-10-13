package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.Toast
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            like = 0,
            shar = 0,
            likeByMe = false,
            sharByMe = false
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likeByMe) {
                likesImage.setImageResource(R.drawable.ic_launcher_like_click_foreground)
            }
            likesImage.setOnClickListener {
                post.likeByMe = !post.likeByMe
                likesImage.setImageResource(if (post.likeByMe) R.drawable.ic_launcher_like_click_foreground else R.drawable.ic_launcher_like_foreground)
                if (post.likeByMe) post.like++ else post.like--
                likesText.text = Transformer.Transform.intTransform(post.like)
            }

            repostsImage.setOnClickListener {
                post.sharByMe = !post.sharByMe
                if (post.sharByMe) post.shar++
                repostsText.text = Transformer.Transform.intTransform(post.shar)
            }
        }
    }
}
