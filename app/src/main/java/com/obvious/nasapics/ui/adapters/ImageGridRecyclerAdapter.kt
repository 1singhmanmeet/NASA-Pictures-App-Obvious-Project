package com.obvious.nasapics.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.obvious.nasapics.data.models.ImageResult
import com.obvious.nasapics.databinding.ImageGridViewHolderBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.squareup.picasso.Transformation
import java.lang.Exception

class ImageGridRecyclerAdapter(val click: (ImageResult, Int) -> Unit) :
    ListAdapter<ImageResult,
            ImageGridRecyclerAdapter.ImageGridViewHolder>(ImageGridDiff()) {
    class ImageGridViewHolder(
        val binding: ImageGridViewHolderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imageResult: ImageResult) {
            Picasso.get().load(imageResult.url)
                .into(object : Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        val height = bitmap?.height
                        setHeight(height!!)
                        binding.image.setImageBitmap(bitmap)
                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                    }

                    fun setHeight(height: Int) {
                        val layoutParams = binding.image.layoutParams
                                as ConstraintLayout.LayoutParams
                        layoutParams.height = height
                        binding.image.layoutParams = layoutParams
                        binding.image.requestLayout()
                    }

                })
        }

    }


    class ImageGridDiff : DiffUtil.ItemCallback<ImageResult>() {
        override fun areItemsTheSame(oldItem: ImageResult, newItem: ImageResult): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ImageResult, newItem: ImageResult): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageGridViewHolder(
            ImageGridViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: ImageGridViewHolder, position: Int) {
        holder.bind(getItem(holder.bindingAdapterPosition))

        holder.binding.root.setOnClickListener {
            click.invoke(
                getItem(holder.bindingAdapterPosition), holder.bindingAdapterPosition
            )
        }
    }
}