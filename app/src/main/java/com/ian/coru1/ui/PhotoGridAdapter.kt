package com.ian.coru1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ian.coru1.databinding.ItemPhotoBinding
import com.ian.coru1.model.Photo

class PhotoGridAdapter : RecyclerView.Adapter<PhotoGridAdapter.PhotoGridViewHolder>() {
    private var photoList = ArrayList<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PhotoGridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoGridViewHolder, position: Int) {
        holder.bind(this.photoList[position])
    }

    override fun getItemCount(): Int {
        return this.photoList.size
    }

    //activity 에서 사용할 submitList
    fun submitList(photoList : ArrayList<Photo>){
        this.photoList = photoList
    }

    inner class PhotoGridViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photoItem: Photo) {
            binding.photoItem = photoItem
        }
    }
}
