package com.michaelcarrano.detectivedroid.presentation.applist

import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.michaelcarrano.detectivedroid.databinding.AppItemBinding
import com.michaelcarrano.detectivedroid.presentation.model.AppUiModel

typealias ClickListener = (AppUiModel) -> Unit

class AppAdapter(
    private val packageManager: PackageManager,
    private val clickListener: ClickListener,
) : Adapter<AppAdapter.ViewHolder>() {

    private var apps = emptyList<AppUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AppItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)
        binding.root.setOnClickListener { clickListener(apps[viewHolder.bindingAdapterPosition]) }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = apps[position]
        holder.icon.setImageDrawable(packageManager.getApplicationIcon(app.packageName))
        holder.icon.contentDescription.toString().format(app.name)
        holder.name.text = app.name
        holder.version.text = app.versionName
    }

    override fun getItemCount() = apps.size

    fun updateApps(apps: List<AppUiModel>) {
        val diffResult = DiffUtil.calculateDiff(ItemDiffCallback(this.apps, apps))
        this.apps = apps
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(binding: AppItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val icon: ImageView = binding.icon
        val name: TextView = binding.name
        val version: TextView = binding.version
    }
}
