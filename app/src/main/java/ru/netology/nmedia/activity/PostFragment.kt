package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.*
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.util.LongArg


class PostFragment : Fragment() {
    val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)

    lateinit var post: Post
    private lateinit var binding: FragmentPostBinding
    private var id: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        id = arguments?.id


        val viewHolder = PostViewHolder(binding.postOne,
            interactionListener = object : OnInteractionListener {
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
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                    if (activity?.packageManager?.let { intent.resolveActivity(it) } != null) {
                        startActivity(intent)
                    }
                }

                override fun onClickPost(post: Post) {
                    return@onClickPost
                }
            })

        val postId = arguments?.id ?: -1
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId } ?: kotlin.run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(post)
        }

        viewModel.edited.observe(viewLifecycleOwner) {
            if (it.id == 0L) {
                return@observe
            }
            findNavController().navigate(
                R.id.action_postFragment_to_postContentFragment,
                Bundle().apply { textArg = it.content }
            )
        }

        return binding.root
    }


    companion object {
        var Bundle.id: Long by LongArg
    }

}


