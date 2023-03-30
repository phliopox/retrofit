package com.ian.coru1

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ian.coru1.databinding.ActivityMainBinding
import com.ian.coru1.retrofit.RetrofitManager
import com.ian.coru1.utils.Constants
import com.ian.coru1.utils.SEARCH_TYPE
import com.ian.coru1.utils.onMyTextChanged

class MainActivity : AppCompatActivity() {
    private var currentSearchType: SEARCH_TYPE = SEARCH_TYPE.PHOTO
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(Constants.TAG, "MainActivity - onCreate: called")

        //라디오 그룹
        binding.searchTermRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.photo_radio ->{
                    Log.d(Constants.TAG, "사진검색 버튼 클릭")
                    binding.searchTermTextLayout.hint = "사진검색"
                    binding.searchTermTextLayout.startIconDrawable = resources.getDrawable(R.drawable.ic_photo_libary_black_24dp,resources.newTheme())
                    this.currentSearchType = SEARCH_TYPE.PHOTO
                }
                R.id.user_radio ->{
                    Log.d(Constants.TAG, "사용자 검색 버튼 클릭")
                    binding.searchTermTextLayout.hint = "사용자검색"
                    binding.searchTermTextLayout.startIconDrawable = resources.getDrawable(R.drawable.ic_person_black_24dp,resources.newTheme())
                    this.currentSearchType = SEARCH_TYPE.USER

                }
            }
            Log.d(Constants.TAG, "MainActivity - checked change: $currentSearchType ");
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
            Log.d("T", "검색버튼 클릭 $currentSearchType")
            RetrofitManager.instance.searchPhotos(searchTerm = binding.searchTermEditText.text.toString(), completion = {
                Log.d("response",it)

            })
            this.handleSearchButtonUi()
        }
    }
    private fun handleSearchButtonUi() {
        binding.included.btnProgress.visibility = View.VISIBLE
        binding.included.searchBtn.text =""

        Handler().postDelayed({
            binding.included.btnProgress.visibility = View.INVISIBLE
            binding.included.searchBtn.text ="검색"
        }, 1500)
    }
}