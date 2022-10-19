package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding

class PostAdapter(
    private val likeClickListener: (Post) -> Unit,
    private val sharClickListener: (Post) -> Unit
) : RecyclerView.Adapter<PostViewHolder>() {
    var post: List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, likeClickListener, sharClickListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postOne = post[position]
        holder.bind(postOne)
    }

    override fun getItemCount(): Int = post.size
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
