package com.boss.poetrydb2.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.boss.poetrydb2.R
import com.boss.poetrydb2.model.RandomPoem
import kotlinx.android.synthetic.main.layout_item.view.*

class RandomPoemAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RandomPoem>() {

        override fun areItemsTheSame(oldItem: RandomPoem, newItem: RandomPoem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: RandomPoem, newItem: RandomPoem): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return RandomPoemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RandomPoemViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<RandomPoem>) {
        differ.submitList(list)
    }

    class RandomPoemViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: RandomPoem) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.text.text=item.author
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: RandomPoem)
    }
}