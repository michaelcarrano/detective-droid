package com.michaelcarrano.detectivedroid.presentation.appdetail

import androidx.recyclerview.widget.DiffUtil
import com.michaelcarrano.detectivedroid.presentation.model.LibraryUiModel

class ItemDiffCallback(
    private val old: List<LibraryUiModel>,
    private val new: List<LibraryUiModel>,
) : DiffUtil.Callback() {
    override fun getOldListSize() = old.size

    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldIndex: Int, newIndex: Int): Boolean {
        return old[oldIndex].classPath == new[newIndex].classPath
    }

    override fun areContentsTheSame(oldIndex: Int, newIndex: Int): Boolean {
        return old[oldIndex] == new[newIndex]
    }
}
