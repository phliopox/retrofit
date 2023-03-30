package com.ian.coru1.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ian.coru1.databinding.PhotoActivityBinding
import com.ian.coru1.model.Photo
import com.ian.coru1.utils.Constants.TAG

class PhotoCollectionActivity : AppCompatActivity() {
    lateinit var binding : PhotoActivityBinding
    var photoList = ArrayList<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PhotoActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("array_bundle")
        val searchTerm = intent.getStringExtra("search_term")
        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>
        Log.d(TAG, "PhotoCollectionActivity - onCreate: $photoList");

        val adapter = PhotoGridAdapter()
        binding.myPhotoRv.adapter = adapter
        adapter.submitList(photoList)
    }
}