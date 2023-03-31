package com.ian.coru1.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ian.coru1.databinding.ItemSearchHistoryBinding
import com.ian.coru1.model.SearchData
import com.ian.coru1.utils.Constants.TAG

class SearchViewAdapter :
    RecyclerView.Adapter<SearchViewAdapter.SearchViewHolder>(){
    private var searchDataList = ArrayList<SearchData>()

    inner class SearchViewHolder(private val binding: ItemSearchHistoryBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener{
        init {
            binding.layoutRow.setOnClickListener(this)
            binding.xBtn.setOnClickListener(this)
        }
        fun bind(searchData: SearchData) {
            binding.searchData = searchData
        }

        override fun onClick(v: View?) {
            when(v){
                binding.xBtn ->{
                    Log.d(TAG, "SearchViewHolder - onClick: xbtn");
                }
                binding.layoutRow ->{
                    Log.d(TAG, "SearchViewHolder - onClick: row");
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding =
            ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(this.searchDataList[position])
    }

    override fun getItemCount(): Int {
        return this.searchDataList.size
    }

    fun submitList(searchDataList: ArrayList<SearchData>) {
        this.searchDataList = searchDataList
    }
}