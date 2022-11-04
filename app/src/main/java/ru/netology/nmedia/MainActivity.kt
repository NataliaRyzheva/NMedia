package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding


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
            viewModel.edited.observe(this) {
                if (it.id != 0L) {
                    binding.contentSave.setText(it.content)
                    binding.layoutCancel.visibility = View.VISIBLE
                    binding.contentSave.requestFocus()
                } else {
                    binding.layoutCancel.visibility = View.GONE
                    binding.contentSave.clearFocus()

                }
            }
            binding.save.setOnClickListener {

                with(binding.contentSave) {
                    val text = text.toString()
                    if (text.isBlank()) {
                        Toast.makeText(
                            this@MainActivity,
                            R.string.error_empty_content,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    viewModel.changeContent(text)
                    viewModel.save()
                    setText("")
                    clearFocus()
                    AndroidUtils.hideKeyboard(it)
                }
            }

            binding.imageCancel.setOnClickListener {
                with(binding.contentSave) {
                    viewModel.save()
                    clearFocus()
                    binding.layoutCancel.visibility = View.GONE
                    AndroidUtils.hideKeyboard(it)
                    setText("")

                }
            }
        }

    }
}





