package com.michaelcarrano.detectivedroid.presentation.applist

import androidx.recyclerview.widget.DiffUtil
import com.michaelcarrano.detectivedroid.presentation.model.AppUiModel

class ItemDiffCallback(
    private val old: List<AppUiModel>,
    private val new: List<AppUiModel>,
) : DiffUtil.Callback() {
    override fun getOldListSize() = old.size

    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldIndex: Int, newIndex: Int): Boolean {
        return old[oldIndex].packageName == new[newIndex].packageName
    }

    override fun areContentsTheSame(oldIndex: Int, newIndex: Int): Boolean {
        return old[oldIndex] == new[newIndex]
    }
}
