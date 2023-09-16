package com.michaelcarrano.detectivedroid.presentation.appdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.michaelcarrano.detectivedroid.databinding.LibraryItemBinding
import com.michaelcarrano.detectivedroid.presentation.model.LibraryUiModel

typealias ClickListener = (LibraryUiModel) -> Unit

class LibraryAdapter(
    private var libraries: List<LibraryUiModel>,
    private val clickListener: ClickListener,
) : RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LibraryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)
        binding.root.setOnClickListener { clickListener(libraries[viewHolder.bindingAdapterPosition]) }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val library = libraries[position]
        holder.name.text = library.name
    }

    override fun getItemCount() = libraries.size

    fun updateLibraries(newLibraries: List<LibraryUiModel>) {
        val diffResult = DiffUtil.calculateDiff(ItemDiffCallback(libraries, newLibraries))
        libraries = newLibraries
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(binding: LibraryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.name
    }
}
