package com.ian.coru1.ui

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.ian.coru1.R
import com.ian.coru1.databinding.PhotoActivityBinding
import com.ian.coru1.model.Photo
import com.ian.coru1.model.SearchData
import com.ian.coru1.utils.Constants.TAG
import com.ian.coru1.utils.SharedPrefManager
import com.ian.coru1.utils.toFormatString
import java.util.*

class PhotoCollectionActivity : AppCompatActivity(),
    SearchView.OnQueryTextListener,
    CompoundButton.OnCheckedChangeListener,
    View.OnClickListener {
    private lateinit var binding: PhotoActivityBinding
    private var photoList = ArrayList<Photo>()
    private var searchHistoryList: ArrayList<SearchData> = ArrayList()
    private lateinit var mySearchView: SearchView
    private lateinit var mySearchViewEditText: EditText
    private lateinit var searchViewAdapter: SearchViewAdapter

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

        //검색어뷰 리스너 연결
        binding.searchHistorySwitch.setOnCheckedChangeListener(this)
        binding.clearSearchHistoryBtn.setOnClickListener(this)

        //저장된 검색 기록 가져오기
        this.searchHistoryList = SharedPrefManager.getSearchHistory() as ArrayList<SearchData>

        //서치뷰 adapter
        searchHistoryRVSetting(searchHistoryList)

    }
    private fun searchHistoryRVSetting(searchHistoryList : ArrayList<SearchData>){
        searchViewAdapter = SearchViewAdapter()
        binding.searchHistoryRv.apply {
            scrollToPosition(searchViewAdapter.itemCount - 1) //최신거 맨위
            adapter = searchViewAdapter
        }
        searchViewAdapter.submitList(searchHistoryList)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "PhotoCollectionActivity - onCreateOptionsMenu: called")
        val inflater = menuInflater
        inflater.inflate(R.menu.top_app_bar_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        this.mySearchView = menu?.findItem(R.id.search_menu_item)?.actionView as SearchView
        this.mySearchView.apply {
            this.queryHint = "검색어를 입력해주세요"
            this.setOnQueryTextFocusChangeListener { _, hasExpanded ->
                when (hasExpanded) {
                    true -> {
                        Log.d(TAG, "PhotoCollectionActivity - onCreateOptionsMenu:서치뷰열림 ")
                        binding.searchHistoryView.visibility = View.VISIBLE
                    }
                    false -> {
                        binding.searchHistoryView.visibility = View.INVISIBLE
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

        if (!query.isNullOrEmpty()) {
            binding.topAppBar.title = query

            //TODO api 호출 ,검색어저장
            val newSearchData = SearchData(term = query, timestamp = Date().toFormatString())
            this.searchHistoryList.add(newSearchData)
            SharedPrefManager.storeSearchHistoryList(this.searchHistoryList as MutableList<SearchData>)
            this.searchViewAdapter.notifyDataSetChanged()
        }

        //query 비우고, 타자기 내리기
        this.mySearchView.setQuery("", false)
        this.mySearchView.clearFocus()
        binding.topAppBar.collapseActionView()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val userInputText = newText ?: ""
        if (userInputText.count() == 12) {
            Toast.makeText(this, "검색어는 12자 까지만 입력 가능합니다.", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onCheckedChanged(switch: CompoundButton?, isChecked: Boolean) {
        when (switch) {
            binding.searchHistorySwitch -> {
                if (isChecked) {
                    Log.d("switch !", "검색어 저장 기능 on")
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.clearSearchHistoryBtn -> {
                Log.d("검색 기록", "삭제버튼 클릭")
            }
        }
    }
}
