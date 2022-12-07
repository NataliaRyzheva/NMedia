package ru.netology.nmedia.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.*
import ru.netology.nmedia.util.AndroidUtils.hideKeyboard
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.LongArg
import ru.netology.nmedia.util.StringArg

class NewPostFragment : Fragment() {
    val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)
    lateinit var post: Post

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        arguments?.textArg?.let {
            binding.content.setText(it)

        }

        binding.ok.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isNotBlank()) {
                viewModel.changeContent(text)
                viewModel.save()
                hideKeyboard(requireView())

            }
            findNavController().navigateUp()
        }


        val callbackNoEdit = object : OnBackPressedCallback(viewModel.edited.value?.id != 0L) {
            override fun handleOnBackPressed() {
                viewModel.clearEdited()
                viewModel.clearDraft()
                findNavController().navigateUp()
            }
        }
        val callbackWithDraft = object : OnBackPressedCallback(viewModel.edited.value?.id == 0L) {
            override fun handleOnBackPressed() {
                if (binding.content.toString().trim().isNotBlank()) {
                    viewModel.draftContent.value = binding.content.text.toString()
                }
                findNavController().navigateUp()
            }
        }
        val backPressedDispatcher = requireActivity().onBackPressedDispatcher
        backPressedDispatcher.addCallback(viewLifecycleOwner, callbackNoEdit)
        backPressedDispatcher.addCallback(viewLifecycleOwner, callbackWithDraft)
        return binding.root
    }

    companion object {
        var Bundle.idEditedPostArg: Long by LongArg
        var Bundle.textArg by StringArg
    }

}