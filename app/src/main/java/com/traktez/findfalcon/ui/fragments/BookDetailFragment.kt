package com.traktez.findfalcon.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.traktez.findfalcon.R
import com.traktez.findfalcon.databinding.FragmentBookDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.util.Date


@AndroidEntryPoint
class BookDetailFragment : Fragment() {

    private lateinit var _binding: FragmentBookDetailBinding
    private val binding get() = _binding
    private val viewModel by viewModels<BookDetailViewModel>()
    private val args by navArgs<BookDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        val data = args.bookEntity
        with(binding) {
            title.text = data.title
            hits.text = "Hits: ${data.hits}"
            alias.text = "Alias: ${data.alias}"
            val date = Date(data.lastChapterDate)
            val formattedDate = DateFormat.getDateInstance().format(date)
            updatedOn.text = "Updated on: $formattedDate"

            Glide.with(img).load(data.image).into(img)

            if (data.isBookMarked) {
                bookmark.setImageDrawable(
                    AppCompatResources.getDrawable(
                        binding.root.context, R.drawable.ic_liked
                    )
                )
            } else {
                bookmark.setImageDrawable(
                    AppCompatResources.getDrawable(
                        binding.root.context, R.drawable.ic_unliked
                    )
                )
            }

            bookmark.setOnClickListener {
                if (data.isBookMarked) {
                    bookmark.setImageDrawable(
                        AppCompatResources.getDrawable(
                            binding.root.context, R.drawable.ic_unliked
                        )
                    )
                    data.isBookMarked = false
                    viewModel.unBookMarkBook(data.id)
                } else {
                    bookmark.setImageDrawable(
                        AppCompatResources.getDrawable(
                            binding.root.context, R.drawable.ic_liked
                        )
                    )
                    data.isBookMarked = true
                    viewModel.bookMarkBook(data.id)
                }
            }
        }
        return binding.root
    }
}