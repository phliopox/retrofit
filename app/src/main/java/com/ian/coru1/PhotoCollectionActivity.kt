package com.ian.coru1

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.ian.coru1.databinding.PhotoActivityBinding

class PhotoCollectionActivity : AppCompatActivity() {
    lateinit var binding : PhotoActivityBinding
    var photoList = ArrayList<Photo>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = PhotoActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("array_bundle")
        val searchTerm = intent.getStringExtra("search_term")

        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>

    }
}