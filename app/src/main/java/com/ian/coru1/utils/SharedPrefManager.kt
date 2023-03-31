package com.ian.coru1.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.ian.coru1.App
import com.ian.coru1.model.SearchData
import com.ian.coru1.utils.Constants.TAG

//싱글톤을 위해 object 로 !
object SharedPrefManager {
    private const val SHARED_SEARCH_HISTORY = "shared_search_history"
    private const val KEY_SEARCH_HISTORY = "key_search_history"

    //검색 목록 저장
    fun storeSearchHistoryList(searchHistoryList: MutableList<SearchData>) {
        Log.d(TAG, "storeSearchHistoryList: called")

        //매개변수로 들어온 배열을 문자열로 변환
        val searchHistoryListString: String = Gson().toJson(searchHistoryList)
        Log.d(TAG, "SharedPrefManager - storeSearchHistoryList: $searchHistoryListString");

        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)
        val editor = shared.edit()
        editor.putString(KEY_SEARCH_HISTORY, searchHistoryListString).apply()

    }

    fun getSearchHistory(): MutableList<SearchData> {
        val shared = App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)
        val storedSearchHistoryListString = shared.getString(KEY_SEARCH_HISTORY, "")!!

        var storedSearchHistoryList = ArrayList<SearchData>()
        if (storedSearchHistoryListString.isNotEmpty()) {
            storedSearchHistoryList =
                Gson().fromJson(storedSearchHistoryListString, Array<SearchData>::class.java)
                    .toMutableList() as ArrayList<SearchData>
        }
        return storedSearchHistoryList
    }

    fun deletePref(){
        val shared =
            App.instance.getSharedPreferences(SHARED_SEARCH_HISTORY, Context.MODE_PRIVATE)
        val edit = shared.edit()
        edit.remove(KEY_SEARCH_HISTORY).apply()
    }
}