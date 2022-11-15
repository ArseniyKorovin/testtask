package ru.homedevelopment.testtask.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.homedevelopment.testtask.R
import ru.homedevelopment.testtask.databinding.ItemImageBinding

class MainAdapter(
    private val images: List<String>,
    private val onClickListener: (imageUrl: String) -> Unit,
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context))
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.image.apply {
            load(images[position]) {
                crossfade(true)
                placeholder(R.drawable.image)
                cropToPadding = false
            }
            setOnClickListener { onClickListener(images[position]) }
        }

    }

    override fun getItemCount(): Int = images.size

    inner class MainViewHolder(
        val binding: ItemImageBinding
    ) : RecyclerView.ViewHolder(binding.root)
}