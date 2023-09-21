package com.traktez.findfalcon.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.traktez.findfalcon.R
import com.traktez.findfalcon.data.entity.BookItemEntity
import com.traktez.findfalcon.databinding.ItemBookBinding
import javax.inject.Inject

class BookListAdapter @Inject constructor() : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    private val bookList = arrayListOf<BookItemEntity>()
    private var bookItemCallBack: BookItemCallBack? = null

    inner class ViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: BookItemEntity) {
            with(binding) {
                title.text = data.title
                hits.text = "Hits: ${data.hits}"

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
                        bookItemCallBack?.unBookmarkClicked(data.id)
                    } else {
                        bookmark.setImageDrawable(
                            AppCompatResources.getDrawable(
                                binding.root.context, R.drawable.ic_liked
                            )
                        )
                        data.isBookMarked = true
                        bookItemCallBack?.favouriteClicked(data.id)
                    }
                }
                root.setOnClickListener {
                    bookItemCallBack?.itemClicked(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bookList[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun setBookList(list: ArrayList<BookItemEntity>) {
        bookList.clear()
        bookList.addAll(list)
        notifyDataSetChanged()
    }

    fun setCallBack(callBack: BookItemCallBack) {
        bookItemCallBack = callBack
    }

}

interface BookItemCallBack {
    fun itemClicked(item: BookItemEntity)
    fun favouriteClicked(id: String)
    fun unBookmarkClicked(id: String)
}