package com.obvious.nasapics.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.obvious.nasapics.data.models.ImageResult
import com.obvious.nasapics.databinding.ImagePageViewBinding
import com.squareup.picasso.Picasso

class ImageViewPagerAdapter(var imageList:List<ImageResult>,val click:(Int)->Unit)
    :RecyclerView.Adapter<ImageViewPagerAdapter.ImagePageViewHolder>(){
    class ImagePageViewHolder(val binding:ImagePageViewBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun bind(imageResult: ImageResult){
            Picasso.get().load(imageResult.url).into(binding.mainImage)
            binding.title.text=imageResult.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    = ImagePageViewHolder(
        ImagePageViewBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
    )

    override fun onBindViewHolder(holder: ImagePageViewHolder, position: Int) {
        holder.bind(imageList[holder.bindingAdapterPosition])
        holder.binding.mainImage.setOnClickListener {
            click.invoke(holder.bindingAdapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}