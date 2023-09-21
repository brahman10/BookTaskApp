package com.traktez.findfalcon.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.traktez.findfalcon.R
import com.traktez.findfalcon.data.entity.BookItemEntity
import com.traktez.findfalcon.data.entity.State
import com.traktez.findfalcon.databinding.FragmentFindFalconBinding
import com.traktez.findfalcon.ui.adapters.BookItemCallBack
import com.traktez.findfalcon.utils.NetworkUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/** we can create BaseFragment as well to do some common work
we can use to inflate views directly in base fragment and so on.
I'm not using base fragment in this project as its just two fragment project
 */

@AndroidEntryPoint
class BookListFragment : Fragment(), BookItemCallBack {

    private lateinit var _binding: FragmentFindFalconBinding
    private val binding get() = _binding
    private val viewModel by viewModels<BookListViewModel>()

    @Inject
    lateinit var networkUtil: NetworkUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFindFalconBinding.inflate(inflater, container, false)
        initListeners()
        initObservers()
        getData()
        return binding.root
    }

    private fun getData() {
        if (networkUtil.hasNetwork()) {
            viewModel.getData()
        } else {
            // show no network screen here.
        }
    }

    private fun initListeners() {
        viewModel.bookListAdapter.setCallBack(this)
        binding.recyclerBooks.adapter = viewModel.bookListAdapter
        binding.rgSort.setOnCheckedChangeListener { _, id ->
            when (id) {
                binding.rbTitle.id -> {
                    viewModel.sortBy = "title"
                }

                binding.rbHits.id -> {
                    viewModel.sortBy = "hits"
                }

                else -> {
                    viewModel.sortBy = "favs"
                }
            }
            viewModel.sortAdapterList()
        }
        binding.logout.setOnClickListener {
            viewModel.appPreferences.clearPreference()
            findNavController().navigate(BookListFragmentDirections.toSignUp())
        }
        binding.btnSort.setOnClickListener {
            viewModel.isAscending = !viewModel.isAscending
            if (viewModel.isAscending) {
                binding.btnSort.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_desc
                    )
                )
            } else {
                binding.btnSort.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_ass
                    )
                )
            }
            viewModel.updateSorting()
        }
    }

    private fun initObservers() {
        viewModel.booksLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Success -> {
                    viewModel.updateAdapterList(it.data)
                }

                is State.Error -> {
                    //show error screen here
                }

                is State.Loading -> {
                    // show shimmer here

                }
            }
        }
    }

    override fun itemClicked(item: BookItemEntity) {
        findNavController().navigate(BookListFragmentDirections.toBookDetail(item))
    }

    override fun favouriteClicked(id: String) {
        viewModel.bookMarkBook(id)
    }

    override fun unBookmarkClicked(id: String) {
        viewModel.unBookMarkBook(id)
    }


}