package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding

interface OnInteractionListener {
    fun onEdit(post: Post)
    fun onRemove(post: Post)
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onPlayVideoClicked(post: Post)
}

class PostAdapter(
    private val interactionListener: OnInteractionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}


class PostViewHolder(
    private val binding: CardPostBinding,
    private val interactionListener: OnInteractionListener


) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likesImage.text = ru.netology.nmedia.Transformer.Transform.intTransform(post.like)
            repostsImage.text = ru.netology.nmedia.Transformer.Transform.intTransform(post.shar)
            likesImage.isChecked = post.likeByMe
            if (post.video != null) {
                layoutVideo.visibility = View.VISIBLE
            }

            // likesImage.setImageResource(
            //     if (post.likeByMe) ru.netology.nmedia.R.drawable.ic_launcher_like_click_foreground else ru.netology.nmedia.R.drawable.ic_launcher_like_foreground
            // )
            videoBanner.setOnClickListener {
                interactionListener.onPlayVideoClicked(post)
            }
            playClickVideo.setOnClickListener {
                interactionListener.onPlayVideoClicked(post)
            }
            likesImage.setOnClickListener {
                interactionListener.onLike(post)
            }
            repostsImage.setOnClickListener {
                interactionListener.onShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                interactionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                interactionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}
