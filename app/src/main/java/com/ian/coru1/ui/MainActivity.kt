package com.ian.coru1.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ian.coru1.R
import com.ian.coru1.databinding.ActivityMainBinding
import com.ian.coru1.retrofit.RetrofitManager
import com.ian.coru1.utils.Constants.TAG
import com.ian.coru1.utils.SEARCH_TYPE
import com.ian.coru1.utils.onMyTextChanged

class MainActivity : AppCompatActivity() {
    private var currentSearchType: SEARCH_TYPE = SEARCH_TYPE.PHOTO
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //라디오 그룹
        binding.searchTermRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.photo_radio ->{
                    binding.searchTermTextLayout.hint = "사진검색"
                    binding.searchTermTextLayout.startIconDrawable = resources.getDrawable(R.drawable.ic_photo_libary_black_24dp,resources.newTheme())
                    this.currentSearchType = SEARCH_TYPE.PHOTO
                }
                R.id.user_radio ->{
                    binding.searchTermTextLayout.hint = "사용자검색"
                    binding.searchTermTextLayout.startIconDrawable = resources.getDrawable(R.drawable.ic_person_black_24dp,resources.newTheme())
                    this.currentSearchType = SEARCH_TYPE.USER

                }
            }
        }

        //text가 변경이 되었을때
        binding.searchTermEditText.onMyTextChanged {
            if (it.toString().isNotEmpty()) {
                binding.included.searchBtnFrame.visibility= View.VISIBLE
                binding.searchTermTextLayout.helperText = ""
                //스크롤뷰를 올린다.
                binding.scrollLayout.scrollTo(0, 200)

            }else{
                binding.included.searchBtnFrame.visibility = View.INVISIBLE
            }

            if (it.toString().count() == 12) {
                Toast.makeText(this,"검색어는 12자 까지만 입력 가능합니다.",Toast.LENGTH_SHORT).show()
            }
        }

        binding.included.searchBtn.setOnClickListener {
            val edittext = binding.searchTermEditText.text.toString()
            RetrofitManager.instance.searchPhotos(searchTerm = edittext, completion = {responseDataArrayList->
                val intent = Intent(this, PhotoCollectionActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("photo_array_list",responseDataArrayList)
                intent.putExtra("array_bundle",bundle)
                intent.putExtra("search_term",edittext)
                Log.d(TAG, "MainActivity - onCreate: $responseDataArrayList");
                startActivity(intent)
            })
            this.handleSearchButtonUi()
        }
    }
    private fun handleSearchButtonUi() {
        binding.included.btnProgress.visibility = View.VISIBLE
        binding.included.searchBtn.visibility =View.INVISIBLE

        Handler().postDelayed({
            binding.included.btnProgress.visibility = View.INVISIBLE
            binding.included.searchBtn.visibility =View.VISIBLE
        }, 1500)
    }
}