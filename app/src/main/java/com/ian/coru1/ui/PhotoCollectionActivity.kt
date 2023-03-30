package com.ian.coru1.ui

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.widget.EditText

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.ian.coru1.R
import com.ian.coru1.databinding.PhotoActivityBinding
import com.ian.coru1.model.Photo
import com.ian.coru1.utils.Constants.TAG

class PhotoCollectionActivity : AppCompatActivity(),SearchView.OnQueryTextListener {
    lateinit var binding : PhotoActivityBinding
    var photoList = ArrayList<Photo>()

    private lateinit var mySearchView : SearchView
    private lateinit var mySearchViewEditText :EditText
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = PhotoActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("array_bundle")
        val searchTerm = intent.getStringExtra("search_term")
        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>

        val adapter = PhotoGridAdapter()
        binding.myPhotoRv.adapter = adapter
        adapter.submitList(photoList)

        binding.topAppBar.title = searchTerm

        setSupportActionBar(binding.topAppBar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "PhotoCollectionActivity - onCreateOptionsMenu: called")
        val inflater = menuInflater
        inflater.inflate(R.menu.top_app_bar_menu,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        this.mySearchView = menu?.findItem(R.id.search_menu_item)?.actionView as SearchView
        this.mySearchView.apply {
            this.queryHint = "검색어를 입력해주세요"
            this.setOnQueryTextFocusChangeListener{ _,hasExpanded->
                when(hasExpanded){
                    true->{
                        Log.d(TAG, "PhotoCollectionActivity - onCreateOptionsMenu:서치뷰열림 ");
                    }
                    false->{

                    }
                }
            }
            this.setOnQueryTextListener(this@PhotoCollectionActivity)
            mySearchViewEditText = this.findViewById(androidx.appcompat.R.id.search_src_text)
        }
        this.mySearchViewEditText.apply {
            this.filters = arrayOf(InputFilter.LengthFilter(12))
            this.setTextColor(Color.WHITE)
            this.setHintTextColor(Color.WHITE)
        }

        return true
    }

    //서치뷰 검색어 입력 이벤트
    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val userInputText = newText ?:""
        if (userInputText.count() == 12) {
            Toast.makeText(this,"검색어는 12자 까지만 입력 가능합니다.",Toast.LENGTH_SHORT).show()
        }
        return true
    }
}