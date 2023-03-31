package com.ian.coru1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ian.coru1.databinding.ItemSearchHistoryBinding
import com.ian.coru1.model.SearchData

class SearchViewAdapter(private val searchViewClickListener: SearchViewClickListener) :
    RecyclerView.Adapter<SearchViewAdapter.SearchViewHolder>(){
    private var searchDataList = ArrayList<SearchData>()

    inner class SearchViewHolder(private val binding: ItemSearchHistoryBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(searchData: SearchData) {
            binding.searchData = searchData
          val indexOf = searchDataList.indexOf(searchData)

          binding.xBtn.setOnClickListener{
              searchViewClickListener.onSearchItemDeleteClicked(indexOf)
          }
          binding.layoutRow.setOnClickListener{
              searchViewClickListener.onSearchItemClicked(indexOf)
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