package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likesText.text = Transformer.Transform.intTransform(post.like)
                repostsText.text = Transformer.Transform.intTransform(post.shar)
                likesImage.setImageResource(
                    if (post.likeByMe) R.drawable.ic_launcher_like_click_foreground else R.drawable.ic_launcher_like_foreground
                )
            }
        }

        binding.likesImage.setOnClickListener {
            viewModel.like()
        }
        binding.repostsImage.setOnClickListener {
            viewModel.shar()
        }
    }
}




