package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ru.netology.nmedia.*
import ru.netology.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onLike(post: Post) {
                viewModel.like(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shar(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }

            override fun onPlayVideoClicked(post: Post) {
                Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                    .also {
                        if (it.resolveActivity(packageManager) != null) {
                            startActivity(it)
                        }
                    }
            }
        })

        binding.list.adapter = adapter

        viewModel.data.observe(this) { post ->
            val newPost = adapter.itemCount < post.size
            adapter.submitList(post) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        val activityLauncher = registerForActivityResult(NewPostActivity.Contract) { it ->
            it ?: return@registerForActivityResult
            viewModel.changeContent(it)
            viewModel.changeContentUrl(it)
            viewModel.save()
        }

        viewModel.edited.observe(this) { it ->
            if (it.id == 0L) {
                return@observe
            }
            activityLauncher.launch(it.content)

        }

        binding.add.setOnClickListener {
            activityLauncher.launch(null)
        }

    }

}








