package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding

class PostAdapter(
    private val likeClickListener: (Post) -> Unit,
    private val sharClickListener: (Post) -> Unit
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, likeClickListener, sharClickListener)
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
    private val likeClickListener: (Post) -> Unit,
    private val sharClickListener: (Post) -> Unit

) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likesText.text = ru.netology.nmedia.Transformer.Transform.intTransform(post.like)
            repostsText.text = ru.netology.nmedia.Transformer.Transform.intTransform(post.shar)
            likesImage.setImageResource(
                if (post.likeByMe) ru.netology.nmedia.R.drawable.ic_launcher_like_click_foreground else ru.netology.nmedia.R.drawable.ic_launcher_like_foreground
            )
            likesImage.setOnClickListener {
                likeClickListener(post)
            }
            repostsImage.setOnClickListener {
                sharClickListener(post)
            }
        }
    }

}
